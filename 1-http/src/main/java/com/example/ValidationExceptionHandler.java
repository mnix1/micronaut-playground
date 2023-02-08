package com.example;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = { ValidationException.class })
class ValidationExceptionHandler implements ExceptionHandler<ValidationException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, ValidationException exception) {
        return HttpResponse.status(HttpStatus.I_AM_A_TEAPOT);
    }
}
