package com.example.car.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.ApplicationContextProvider;
import io.micronaut.context.DefaultApplicationContextBuilder;
import io.micronaut.context.env.Environment;
import io.micronaut.function.aws.MicronautLambdaContext;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;

import java.io.Closeable;
import java.io.IOException;

public class CustomHandler extends DefaultApplicationContextBuilder implements RequestHandler<AwsProxyRequest, AwsProxyResponse>, ApplicationContextProvider, Closeable {
    public final MicronautLambdaHandler handler;

    public CustomHandler() {
        this(Environment.FUNCTION, MicronautLambdaContext.ENVIRONMENT_LAMBDA);
    }

    public CustomHandler(String... environments) {
        ApplicationContext customApplicationContext = environments(environments)
                .eagerInitConfiguration(true)
                .build()
                .start();
        try {
            handler = new MicronautLambdaHandler(customApplicationContext);
        } catch (ContainerInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
        return handler.handleRequest(input, context);
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return handler.getApplicationContext();
    }

    @Override
    public void close() {
        handler.close();
    }
}
