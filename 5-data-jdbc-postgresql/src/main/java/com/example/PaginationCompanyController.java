package com.example;

import com.example.CompanyController.ListCompanySnapshot;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/companies")
class PaginationCompanyController {
    private final CompanyRepository repository;

    PaginationCompanyController(CompanyRepository repository) {
        this.repository = repository;
    }

    @Get("/view")
    Page<ListCompanySnapshot> companies(Pageable pageable) {
        return repository.findAll(pageable).map(r -> new ListCompanySnapshot(r.toDomain()));
    }
}
