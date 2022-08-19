package com.example.car;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.DefaultApplicationContextBuilder;
import io.micronaut.context.env.Environment;
import io.micronaut.function.aws.MicronautLambdaContext;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;

public class LambdaRunner extends DefaultApplicationContextBuilder {
    public final MicronautLambdaHandler handler;

    public LambdaRunner() {
        ApplicationContext customApplicationContext = environments(Environment.FUNCTION, MicronautLambdaContext.ENVIRONMENT_LAMBDA)
                .eagerInitConfiguration(true)
                .build()
                .start();
        try {
            handler = new MicronautLambdaHandler(customApplicationContext);
        } catch (ContainerInitializationException e) {
            throw new RuntimeException(e);
        }
    }
}
