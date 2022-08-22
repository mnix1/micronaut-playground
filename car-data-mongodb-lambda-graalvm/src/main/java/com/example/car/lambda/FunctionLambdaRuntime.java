package com.example.car.lambda;

import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class FunctionLambdaRuntime
    extends AbstractMicronautLambdaRuntime<AwsProxyRequest, AwsProxyResponse, AwsProxyRequest, AwsProxyResponse> {

    private static final Logger LOG = LoggerFactory.getLogger(FunctionLambdaRuntime.class);

    public static void main(String[] args) {
        try {
            new FunctionLambdaRuntime().run(args);
        } catch (MalformedURLException e) {
            LOG.error("Lambda startup error", e);
        }
    }

    @Override
    @Nullable
    protected RequestHandler<AwsProxyRequest, AwsProxyResponse> createRequestHandler(String... args) {
        return new CustomHandler();
    }
}
