package com.example.car;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.car.api.CarProducer;
import com.example.car.api.CarSnapshot;
import com.example.car.api.command.ChangeCarOwnerCommand;
import com.example.car.api.command.CreateCarCommand;
import com.example.car.lambda.CustomHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.env.Environment;
import io.micronaut.function.aws.MicronautLambdaContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IntegrationTest {
    static MongoDBContainer container;
    static final Context lambdaContext = new MockLambdaContext();
    static CustomHandler handler;
    ObjectMapper objectMapper = handler.handler.getApplicationContext().getBean(ObjectMapper.class);

    @BeforeAll
    static void beforeAll() {
        container = new MongoDBContainer("mongo:5.0.10")
                .withEnv("MONGO_INITDB_DATABASE", "test");
        container.setPortBindings(List.of("27017:27017"));
        container.start();
        handler = new CustomHandler(Environment.TEST, Environment.FUNCTION, MicronautLambdaContext.ENVIRONMENT_LAMBDA);
    }

    @AfterAll
    static void stopServer() {
        handler.getApplicationContext().close();
        container.stop();
    }

    @Test
    void apiWorks() throws IOException {
        AwsProxyResponse response = executeRequest(listCars());
        assertSuccess(response, 200);
        assertThat(response.getBody()).isEqualTo("[]");

        response = executeRequest(createCar(new CreateCarCommand("CH-R", CarProducer.TOYOTA, 2020)));
        assertSuccess(response, 201);
        String car1Id = objectMapper.readValue(response.getBody(), String.class);
        response = executeRequest(createCar(new CreateCarCommand("XC-40", CarProducer.VOLVO, 2021)));
        assertSuccess(response, 201);
        String car2Id =  objectMapper.readValue(response.getBody(), String.class);

        response = executeRequest(changeOwner(new ChangeCarOwnerCommand(UUID.fromString(car1Id), "Robert")));
        assertSuccess(response, 200);

        response = executeRequest(listCars());
        assertSuccess(response, 200);
        List<CarSnapshot> cars = objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });
        assertThat(cars).hasSize(2);
        assertThat(cars.get(0)).isEqualTo(new CarSnapshot(UUID.fromString(car1Id), "CH-R", CarProducer.TOYOTA, 2020, "Robert"));
        assertThat(cars.get(1)).isEqualTo(new CarSnapshot(UUID.fromString(car2Id), "XC-40", CarProducer.VOLVO, 2021, null));
    }

    private AwsProxyResponse executeRequest(AwsProxyRequest input) {
        return handler.handleRequest(input, lambdaContext);
    }

    private AwsProxyRequest listCars() {
        AwsProxyRequest request = new AwsProxyRequest();
        request.setHttpMethod("GET");
        request.setPath("/cars");
        return request;
    }

    private AwsProxyRequest createCar(CreateCarCommand command) throws JsonProcessingException {
        AwsProxyRequest request = new AwsProxyRequest();
        request.setHttpMethod("POST");
        request.setPath("/cars");
        request.setBody(objectMapper.writeValueAsString(command));
        return request;
    }

    private AwsProxyRequest changeOwner(ChangeCarOwnerCommand command) throws JsonProcessingException {
        AwsProxyRequest request = new AwsProxyRequest();
        request.setHttpMethod("PATCH");
        request.setPath(String.format("/cars/%s/changeOwner", command.id));
        request.setBody(objectMapper.writeValueAsString(Map.of("owner", command.owner)));
        return request;
    }

    private void assertSuccess(AwsProxyResponse response, int expected) {
        assertThat(response.getStatusCode()).isEqualTo(expected);
    }
}