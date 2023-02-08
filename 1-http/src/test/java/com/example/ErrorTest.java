package com.example;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//HTTP-test-3
@MicronautTest
class ErrorTest {

    @Inject
    @Client(value = "/test")
    HttpClient httpClient;

    @Test
    void returnsOkWhenIdEquals1() {
        HttpResponse<TestResponse> response = httpClient.toBlocking().exchange("/1", TestResponse.class);
        assertThat(response.getStatus().getCode()).isEqualTo(200);
        assertThat(response.body().message).isEqualTo("1");
    }

    @Test
    void returnsNotFoundWhenIdGreaterThen1() {
        HttpResponse<String> response = httpClient.toBlocking().exchange("/2", String.class, String.class);
        assertThat(response.getStatus().getCode()).isEqualTo(404);
        assertThat(response.body()).isEqualTo("Id 2 does not exist");
        //TODO ustawić application.yml -> exception-on-error-status=true
    }

    @Controller
    @Requires(bean = ErrorTest.class)
    static class TestController {

        @Get("/test/{id}")
        HttpResponse test(int id) {
            if (id == 1) {
                return HttpResponse.ok(new TestResponse("1"));
            }
            return HttpResponse.notFound("Id " + id + " does not exist");
        }
    }

    record TestResponse(String message) {}
}
