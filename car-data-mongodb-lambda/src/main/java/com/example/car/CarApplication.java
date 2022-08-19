package com.example.car;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.DefaultApplicationContextBuilder;
import io.micronaut.context.env.Environment;
import io.micronaut.function.aws.MicronautLambdaContext;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;

class CarApplication {

    public static void main(String[] args) throws ContainerInitializationException {
        AwsProxyRequest request = new AwsProxyRequest();
        request.setHttpMethod("GET");
        request.setPath("/cars");
//        LambdaApplicationContextBuilder lambdaApplicationContextBuilder = new LambdaApplicationContextBuilder();
//        ApplicationContext lambdaApplicationContext = lambdaApplicationContextBuilder.build();

        DefaultApplicationContextBuilder defaultApplicationContextBuilder = new DefaultApplicationContextBuilder() {
        };
        ApplicationContext customApplicationContext = defaultApplicationContextBuilder
                .environments(Environment.FUNCTION, MicronautLambdaContext.ENVIRONMENT_LAMBDA)
                .eagerInitConfiguration(true)
                .build()
                .start();

//        ApplicationContext micronautApplicationContext = Micronaut.build(args)
////                .classes(new Class[0])
//                .environments(Environment.FUNCTION, MicronautLambdaContext.ENVIRONMENT_LAMBDA)
////                .eagerInitConfiguration(true)
////                .eagerInitSingletons(true)
//                .build()
//                .start();
//        ApplicationContext runApplicationContext = Micronaut.run(args);
        MicronautLambdaHandler handler = new MicronautLambdaHandler(customApplicationContext);
        AwsProxyResponse response = handler.handleRequest(request, new MockLambdaContext());
    }
}
