package com.example;

import com.example.CompanyController.CompanyRequestBody;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.example.CompanyType.PUBLICLY_LISTED;
import static io.micronaut.http.HttpRequest.POST;
import static org.assertj.core.api.Assertions.assertThat;

//HTTP-test-1
@MicronautTest
class IntegrationTest {

    @Inject
    @Client("/companies")
    HttpClient client;

    @BeforeEach
    void setUp() {
        clearCompanies();
    }

    @Test
    void lists() {
        //given
        createGoogleCompany();
        //when
        HttpResponse<List<Company>> response = client.toBlocking().exchange(HttpRequest.GET("/"), Argument.listOf(Company.class));
        //then
        assertOneCompany(response, 10, "Google");
    }

    @Test
    void findsByName() {
        //given
        createGoogleCompany();
        createAppleCompany();
        //when
        HttpResponse<List<Company>> response = client
            .toBlocking()
            .exchange(HttpRequest.GET("/findByName?namePrefix=Ap"), Argument.listOf(Company.class));
        //then
        assertOneCompany(response, 8, "Apple");
    }

    @Test
    void createsMany() {
        //given
        client.toBlocking().exchange(HttpRequest.POST("/random/111", null));
        //when
        HttpResponse<List<Company>> response = client.toBlocking().exchange(HttpRequest.GET("/"), Argument.listOf(Company.class));
        //then
        assertThat(response.body()).hasSize(111);
    }

    private void createGoogleCompany() {
        client.toBlocking().exchange(POST("/", new CompanyRequestBody("Google", "IT", PUBLICLY_LISTED, 10, Instant.now(), List.of())));
    }

    private void createAppleCompany() {
        client.toBlocking().exchange(POST("/", new CompanyRequestBody("Apple", "IT", PUBLICLY_LISTED, 8, Instant.now(), List.of())));
    }

    private void assertOneCompany(HttpResponse<List<Company>> response, int employees, String name) {
        AssertionsForClassTypes.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.body()).hasSize(1);
        assertThat(response.body()).allMatch(c -> c.employees() == employees && c.name().equals(name));
    }

    private void clearCompanies() {
        client.toBlocking().exchange(HttpRequest.DELETE("/"));
    }
}
