package com.example;

import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.audit.AuditEntity;
import com.example.audit.AuditRepository;
import com.example.car.api.CarProducer;
import com.example.car.api.CarSnapshot;
import com.example.car.api.OtherFacade;
import com.example.car.api.command.CreateCarCommand;
import com.example.car.lambda.CustomHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.function.aws.MicronautLambdaContext;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionTest {

    static MongoDBContainer container;
    static final Context lambdaContext = new MockLambdaContext();
    static CustomHandler handler;
    ObjectMapper objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);
    TestOtherFacade testOtherFacade = handler.getApplicationContext().getBean(TestOtherFacade.class);
    AuditRepository auditRepository = handler.getApplicationContext().getBean(AuditRepository.class);

    @BeforeAll
    static void beforeAll() {
        container = new MongoDBContainer("mongo:5.0.10").withEnv("MONGO_INITDB_DATABASE", "test");
        container.setPortBindings(List.of("27017:27017"));
        container.start();
        handler = new CustomHandler(Environment.TEST, Environment.FUNCTION, MicronautLambdaContext.ENVIRONMENT_LAMBDA);
    }

    @AfterAll
    static void stopServer() {
        handler.getApplicationContext().close();
        container.stop();
    }

    @BeforeEach
    void setUp() {
        testOtherFacade.clear();
    }

    @Singleton
    @Primary
    @Requires(classes = TransactionTest.class)
    static class TestOtherFacade implements OtherFacade {

        boolean throwBefore = false;
        boolean throwAfter = false;

        public void clear() {
            throwBefore = false;
            throwAfter = false;
        }

        @Override
        public void somethingBeforeAudit() {
            if (throwBefore) {
                throw new RuntimeException("Error before");
            }
        }

        @Override
        public void somethingAfterAudit() {
            if (throwAfter) {
                throw new RuntimeException("Error after");
            }
        }
    }

    @Test
    void transactionsWork1() throws IOException {
        //given
        testOtherFacade.throwBefore = true;
        //when
        AwsProxyResponse response = executeRequest(createCar(new CreateCarCommand("CH-R", CarProducer.TOYOTA, 2020)));
        assertSuccess(response, 500);
        //then
        response = executeRequest(listCars());
        assertSuccess(response, 200);
        List<CarSnapshot> cars = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(cars).isEmpty();
        List<AuditEntity> audit = StreamSupport.stream(auditRepository.findAll().spliterator(), false).toList();
        assertThat(audit).isEmpty();
    }

    @Test
    void transactionsWork2() throws IOException {
        //given
        testOtherFacade.throwAfter = true;
        //when
        AwsProxyResponse response = executeRequest(createCar(new CreateCarCommand("CH-R", CarProducer.TOYOTA, 2020)));
        assertSuccess(response, 500);
        //then
        response = executeRequest(listCars());
        assertSuccess(response, 200);
        List<CarSnapshot> cars = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(cars).isEmpty();
        List<AuditEntity> audit = StreamSupport.stream(auditRepository.findAll().spliterator(), false).toList();
        assertThat(audit).isEmpty();
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

    private void assertSuccess(AwsProxyResponse response, int expected) {
        assertThat(response.getStatusCode()).isEqualTo(expected);
    }
}
