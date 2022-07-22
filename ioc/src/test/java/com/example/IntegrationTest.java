package com.example;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class IntegrationTest {
    @Inject
    @Client("/vehicles")
    HttpClient client;

    @Test
    void returnsExpensive() {
        HttpResponse<List<String>> response = client.toBlocking().exchange(HttpRequest.GET("/"), Argument.listOf(String.class));
        assertThat(response.body()).contains("Ferrari", "Lamborghini");
    }
}
