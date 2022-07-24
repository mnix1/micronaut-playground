package com.example;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "HTTP Example",
                version = "${api.version}",
                description = "${openapi.description}"
        )
)
class HTTPApplication {
    public static void main(String[] args) {
        Micronaut.run(HTTPApplication.class, args);
    }
}
