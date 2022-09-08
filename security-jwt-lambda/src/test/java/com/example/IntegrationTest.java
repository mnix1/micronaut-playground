package com.example;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.example.CompanyController.CompanyRequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class IntegrationTest {

    static final String VALID_USER_TOKEN =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdC11c2VyIiwiaWF0IjoxNjYyNjY0MDgzLCJleHAiOjI2NjI2NjQwODN9.VhPwqksPlxE22YD1TLFWegfOf0rRVB9W7ixLKImjPSQ";
    static final String VALID_ADMIN_TOKEN =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6InRlc3QtdXNlciIsImlhdCI6MTY2MjY2NDA2NSwiZXhwIjoyNjYyNjY0MDY1fQ.RbgP6pRU3H5dor1CQg9G0KNiNgsKv6xIvrsLkCcNOqk";
    static final String EXPIRED_TOKEN =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sInN1YiI6InRlc3QtdXNlciIsImlhdCI6MTY2MjY2Mzk4MCwiZXhwIjoxNjYyNjYzOTgwfQ.LVD1iEquoZhiWTJhNNdxikTgxws2bDguQWW_YPxxwzQ";
    static final String OTHER_SECRET_TOKEN =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sInN1YiI6InRlc3QtdXNlciIsImlhdCI6MTY2MjY2NDA0NywiZXhwIjoyNjYyNjY0MDQ3fQ.On0tGGDgXbkob6Jhionb_-ZNa-sEFCsAERFUYJfVJCw";
    static final Context lambdaContext = new MockLambdaContext();
    static MicronautLambdaHandler handler;
    ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() throws ContainerInitializationException {
        handler = new MicronautLambdaHandler();
    }

    @AfterAll
    static void stopServer() {
        handler.getApplicationContext().close();
    }

    @BeforeEach
    void setUp() {
        objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);
        //VALID_ADMIN_TOKEN
        clearCompanies();
    }

    @Test
    void lists() throws JsonProcessingException {
        //given VALID_USER_TOKEN
        createGoogleCompany();
        //when VALID_USER_TOKEN
        List<Company> response = list();
        //then
        assertOneCompany(response, 10, "Google");
    }

    @Test
    void findsByName() throws JsonProcessingException {
        //given VALID_USER_TOKEN
        createGoogleCompany();
        createAppleCompany();
        //when VALID_USER_TOKEN
        List<Company> response = findByName("Ap");
        //then
        assertOneCompany(response, 8, "Apple");
    }

    @Test
    void createsMany() throws JsonProcessingException {
        //given no token
        createMany();
        //when VALID_USER_TOKEN
        List<Company> response = list();
        //then
        assertThat(response).hasSize(11);
    }

    @Test
    void throwsUnauthorizedWhenNoToken() {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("DELETE");
        awsProxyRequest.setPath("/companies");
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
        //client.toBlocking().exchange(HttpRequest.DELETE("/"))
    }

    @Test
    void throwsUnauthorizedWhenExpiredToken() {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("GET");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, EXPIRED_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
        //client.toBlocking().exchange(HttpRequest.GET("/").bearerAuth(EXPIRED_TOKEN))
    }

    @Test
    void throwsUnauthorizedWhenSignedByAnotherSecretToken() {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("GET");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, OTHER_SECRET_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
        //client.toBlocking().exchange(HttpRequest.GET("/").bearerAuth(OTHER_SECRET_TOKEN))
    }

    @Test
    void throwsForbiddenWhenWrongRole() {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("DELETE");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, VALID_USER_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.getCode());
        //client.toBlocking().exchange(HttpRequest.DELETE("/").bearerAuth(VALID_USER_TOKEN))
    }

    private void createMany() {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("POST");
        awsProxyRequest.setPath("/companies/random/11");
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(201);
        // client.toBlocking().exchange(HttpRequest.POST("/random/11", null));
    }

    private void addToken(AwsProxyRequest awsProxyRequest, String token) {
        Headers headers = new Headers();
        headers.put(HttpHeaders.AUTHORIZATION, List.of("Bearer " + token));
        awsProxyRequest.setMultiValueHeaders(headers);
    }

    private List<Company> list() throws JsonProcessingException {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("GET");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, VALID_USER_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(200);
        return objectMapper.readValue(awsProxyResponse.getBody(), new TypeReference<>() {});
        //        return client.toBlocking().exchange(HttpRequest.GET(uri).bearerAuth(VALID_USER_TOKEN), Argument.listOf(Company.class));
    }

    private List<Company> findByName(String name) throws JsonProcessingException {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("GET");
        MultiValuedTreeMap<String, String> queryStringParameters = new MultiValuedTreeMap<>();
        queryStringParameters.add("namePrefix", name);
        awsProxyRequest.setMultiValueQueryStringParameters(queryStringParameters);
        awsProxyRequest.setPath("/companies/findByName");
        addToken(awsProxyRequest, VALID_USER_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(200);
        return objectMapper.readValue(awsProxyResponse.getBody(), new TypeReference<>() {});
        //        return client.toBlocking().exchange(HttpRequest.GET(uri).bearerAuth(VALID_USER_TOKEN), Argument.listOf(Company.class));
    }

    private void createGoogleCompany() throws JsonProcessingException {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setBody(objectMapper.writeValueAsString(new CompanyRequestBody("Google", "IT", 10, Instant.now(), List.of())));
        awsProxyRequest.setHttpMethod("POST");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, VALID_USER_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(201);
        //client.toBlocking().exchange(POST("/", new CompanyRequestBody("Google", "IT", 10, Instant.now(), List.of())).bearerAuth(VALID_USER_TOKEN));
    }

    private void createAppleCompany() throws JsonProcessingException {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setBody(objectMapper.writeValueAsString(new CompanyRequestBody("Apple", "IT", 8, Instant.now(), List.of())));
        awsProxyRequest.setHttpMethod("POST");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, VALID_USER_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(201);
        //client.toBlocking().exchange(POST("/", new CompanyRequestBody("Apple", "IT", 8, Instant.now(), List.of())).bearerAuth(VALID_USER_TOKEN));
    }

    private void clearCompanies() {
        AwsProxyRequest awsProxyRequest = new AwsProxyRequest();
        awsProxyRequest.setHttpMethod("DELETE");
        awsProxyRequest.setPath("/companies");
        addToken(awsProxyRequest, VALID_ADMIN_TOKEN);
        AwsProxyResponse awsProxyResponse = executeRequest(awsProxyRequest);
        assertThat(awsProxyResponse.getStatusCode()).isEqualTo(204);
        //client.toBlocking().exchange(HttpRequest.DELETE("/").bearerAuth(VALID_ADMIN_TOKEN));
    }

    private void assertOneCompany(List<Company> response, int employees, String name) {
        assertThat(response).hasSize(1);
        assertThat(response).allMatch(c -> c.employees() == employees && c.name().equals(name));
    }

    private AwsProxyResponse executeRequest(AwsProxyRequest input) {
        AwsProxyRequestContext requestContext = new AwsProxyRequestContext();
        input.setRequestContext(requestContext);
        return handler.handleRequest(input, lambdaContext);
    }
}
