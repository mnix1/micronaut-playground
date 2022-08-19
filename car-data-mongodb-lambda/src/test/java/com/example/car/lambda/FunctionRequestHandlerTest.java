package com.example.car.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionRequestHandlerTest {
    static final Context lambdaContext = new MockLambdaContext();
    static MicronautLambdaHandler handler;

    @BeforeAll
    static void setupSpec() throws ContainerInitializationException {
        handler = new MicronautLambdaHandler();
    }

    @AfterAll
    static void stopServer() {
        if (handler != null) {
            handler.getApplicationContext().close();
        }
    }

    //    @Test
//    public void testHandler() {
//        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
//        request.setHttpMethod("GET");
//        request.setPath("/");
//        APIGatewayProxyResponseEvent response = handler.execute(request);
//        assertEquals(200, response.getStatusCode().intValue());
//        assertEquals("{\"message\":\"Hello World\"}", response.getBody());
//    }
    @Test
    public void testHandler() {
        AwsProxyRequest request = new AwsProxyRequest();
        request.setHttpMethod("GET");
        request.setPath("/cars");
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"Hello World\"}", response.getBody());
    }
}