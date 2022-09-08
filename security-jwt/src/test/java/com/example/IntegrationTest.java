package com.example;

import com.example.CompanyController.CompanyRequestBody;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static io.micronaut.http.HttpRequest.POST;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class IntegrationTest {
    private final static String VALID_USER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdC11c2VyIiwiaWF0IjoxNjYyNjY0MDgzLCJleHAiOjI2NjI2NjQwODN9.VhPwqksPlxE22YD1TLFWegfOf0rRVB9W7ixLKImjPSQ";
    private final static String VALID_ADMIN_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6InRlc3QtdXNlciIsImlhdCI6MTY2MjY2NDA2NSwiZXhwIjoyNjYyNjY0MDY1fQ.RbgP6pRU3H5dor1CQg9G0KNiNgsKv6xIvrsLkCcNOqk";
    private final static String EXPIRED_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sInN1YiI6InRlc3QtdXNlciIsImlhdCI6MTY2MjY2Mzk4MCwiZXhwIjoxNjYyNjYzOTgwfQ.LVD1iEquoZhiWTJhNNdxikTgxws2bDguQWW_YPxxwzQ";
    private final static String OTHER_SECRET_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sInN1YiI6InRlc3QtdXNlciIsImlhdCI6MTY2MjY2NDA0NywiZXhwIjoyNjYyNjY0MDQ3fQ.On0tGGDgXbkob6Jhionb_-ZNa-sEFCsAERFUYJfVJCw";
    @Inject
    @Client("/companies")
    HttpClient client;

    @BeforeEach
    void setUp() {
        //VALID_ADMIN_TOKEN
        clearCompanies();
    }

    @Test
    void lists() {
        //given VALID_USER_TOKEN
        createGoogleCompany();
        //when VALID_USER_TOKEN
        HttpResponse<List<Company>> response = find("/");
        //then
        assertOneCompany(response, 10, "Google");
    }

    @Test
    void findsByName() {
        //given VALID_USER_TOKEN
        createGoogleCompany();
        createAppleCompany();
        //when VALID_USER_TOKEN
        HttpResponse<List<Company>> response = find("/findByName?namePrefix=Ap");
        //then
        assertOneCompany(response, 8, "Apple");
    }

    @Test
    void createsMany() {
        //given no token
        client.toBlocking().exchange(HttpRequest.POST("/random/11", null));
        //when VALID_USER_TOKEN
        HttpResponse<List<Company>> response = find("/");
        //then
        assertThat(response.body()).hasSize(11);
    }

    @Test
    void throwsUnauthorizedWhenNoToken() {
        HttpClientResponseException throwable = (HttpClientResponseException) Assertions.catchThrowable(() -> client.toBlocking().exchange(HttpRequest.DELETE("/")));
        assertThat(throwable.getResponse().code()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }

    @Test
    void throwsUnauthorizedWhenExpiredToken() {
        HttpClientResponseException throwable = (HttpClientResponseException) Assertions.catchThrowable(() -> client.toBlocking().exchange(HttpRequest.GET("/").bearerAuth(EXPIRED_TOKEN)));
        assertThat(throwable.getResponse().code()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }

    @Test
    void throwsUnauthorizedWhenSignedByAnotherSecretToken() {
        HttpClientResponseException throwable = (HttpClientResponseException) Assertions.catchThrowable(() -> client.toBlocking().exchange(HttpRequest.GET("/").bearerAuth(OTHER_SECRET_TOKEN)));
        assertThat(throwable.getResponse().code()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }

    @Test
    void throwsForbiddenWhenWrongRole() {
        HttpClientResponseException throwable = (HttpClientResponseException) Assertions.catchThrowable(() -> client.toBlocking().exchange(HttpRequest.DELETE("/").bearerAuth(VALID_USER_TOKEN)));
        assertThat(throwable.getResponse().code()).isEqualTo(HttpStatus.FORBIDDEN.getCode());
    }

    private HttpResponse<List<Company>> find(String uri) {
        return client.toBlocking().exchange(HttpRequest.GET(uri).bearerAuth(VALID_USER_TOKEN), Argument.listOf(Company.class));
    }

    private void createGoogleCompany() {
        client.toBlocking().exchange(POST("/", new CompanyRequestBody("Google", "IT", 10, Instant.now(), List.of())).bearerAuth(VALID_USER_TOKEN));
    }

    private void createAppleCompany() {
        client.toBlocking().exchange(POST("/", new CompanyRequestBody("Apple", "IT", 8, Instant.now(), List.of())).bearerAuth(VALID_USER_TOKEN));
    }

    private void clearCompanies() {
        client.toBlocking().exchange(HttpRequest.DELETE("/").bearerAuth(VALID_ADMIN_TOKEN));
    }

    private void assertOneCompany(HttpResponse<List<Company>> response, int employees, String name) {
        AssertionsForClassTypes.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.body()).hasSize(1);
        assertThat(response.body()).allMatch(c -> c.employees() == employees && c.name().equals(name));
    }
}
