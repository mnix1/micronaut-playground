package com.example;

import com.example.CompanyController.ListCompanySnapshot;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.data.model.Sort.Order;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class PaginationTest {
    @Inject
    AppClient client;

    @Test
    void supportsPagination() {
        //given
        client.createRandom(100);
        //when
        Page<ListCompanySnapshot> companies = client.listWithPagination(2, 10, "name,desc");
        //then
        assertThat(companies.getTotalPages()).isEqualTo(10);
        assertThat(companies.getTotalSize()).isEqualTo(100);
        assertThat(companies.getNumberOfElements()).isEqualTo(10);
        assertThat(companies.getContent()).hasSize(10);
    }

    @Client("/companies")
    interface AppClient {
        @Post("/random/{count}")
        void createRandom(int count);

        @Get("/view")
        Page<ListCompanySnapshot> listWithPagination(@QueryValue Integer page, @QueryValue Integer size, @QueryValue String sort);
    }
}
