package com.example;

import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("/companies")
interface CompaniesClient {
    @Get
    List<CompanyController.ListCompanySnapshot> list();

    @Post
    Company create(CompanyController.CompanyRequestBody requestBody);

    @Post("/random")
    Company createRandom();

    @Delete
    void delete();
}
