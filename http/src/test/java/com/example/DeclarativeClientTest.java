package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class DeclarativeClientTest {

    @Inject
    CompaniesClient client;

    @BeforeEach
    void setUp() {
        client.delete();
    }

    @Test
    void createsAndLists() {
        //when
        Company randomCompany = client.createRandom();
        Company testCompany = client.create(new CompanyController.CompanyRequestBody("Test", "IT", 1, Instant.now(), List.of()));
        List<CompanyController.ListCompanySnapshot> companies = client.list();
        //then
        assertThat(companies.stream().map(CompanyController.ListCompanySnapshot::id))
            .containsExactlyInAnyOrder(randomCompany.id(), testCompany.id());
    }
}
