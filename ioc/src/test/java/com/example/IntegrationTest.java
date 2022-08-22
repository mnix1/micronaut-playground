package com.example;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(rebuildContext = true)
@ExtendWith(AlwaysRebuildContextJunit5Extension.class)
class IntegrationTest {

    @Inject
    @Client("/vehicles")
    HttpClient client;

    @Test
    @Property(name = "vehicles.expensive", value = "true")
    void returnsExpensive() {
        expectVehicle("Ferrari F8 Spider");
    }

    @Test
    @Property(name = "vehicles.cheap", value = "true")
    void returnsCheap() {
        expectVehicle("Fiat 126p");
    }

    @Test
    @Property(name = "vehicles.suv", value = "true")
    void returnsSUV() {
        expectVehicle("Toyota Rav4", "Toyota Highlander");
    }

    private void expectVehicle(String... names) {
        HttpResponse<List<String>> response = client.toBlocking().exchange(HttpRequest.GET("/"), Argument.listOf(String.class));
        assertThat(response.body()).containsExactly(names);
    }
}
