package com.example;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Controller("/companies")
class CompanyController {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyService companyService;

    CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Post("/random")
    @Status(HttpStatus.CREATED)
    Company createRandom() {
        return companyService.createRandom();
    }

    @Get
    List<ListCompanySnapshot> find(Optional<Integer> employeesFrom, Optional<Integer> employeesTo) {
        return companyService.find(employeesFrom, employeesTo)
            .stream()
            .map(ListCompanySnapshot::new)
            .toList();
    }

    @Get("findByName")
    List<ListCompanySnapshot> findByName(String namePrefix, Optional<String> nameSuffix) {
        return companyService.findByName(namePrefix, nameSuffix)
            .stream()
            .map(ListCompanySnapshot::new)
            .toList();
    }

    @Get("/{id}")
    Optional<Company> get(UUID id, @Header String contentType) {
        LOG.info(contentType);
        return companyService.get(id);
    }

    @Post("/random/{count}")
    HttpResponse createManyRandom(int count) {
        for (int i = 0; i < count; i++) {
            companyService.createRandom();
        }
        return HttpResponse.status(HttpStatus.CREATED).header("RANDOM", "true").header("COUNT", count + "");
    }

    @Delete("/{id}")
    HttpResponse<Void> delete(UUID id) {
        companyService.delete(id);
        return HttpResponse.noContent();
    }

    @Status(HttpStatus.NO_CONTENT)
    @Delete
    void deleteAll() {
        companyService.deleteAll();
    }

    @Post
    HttpResponse<Company> create(@Body @Valid CompanyRequestBody requestBody) {
        if (requestBody.createdDateTime.isAfter(Instant.now())) {
            throw new ValidationException("Wrong createdDateTime");
        }
        Company company = requestBody.toCompany(UUID.randomUUID());
        companyService.create(company);
        return HttpResponse.created(company).header("TEST-HEADER", "true");
    }

    @Introspected
    record CompanyRequestBody(
        @NotBlank String name,
        @NotBlank String industry,
        CompanyType type,
        @Min(0) int employees,
        @NotNull Instant createdDateTime,
        List<Facility> facilities
    ) {
        Company toCompany(UUID id) {
            return new Company(id, name, industry, type, employees, createdDateTime, facilities);
        }
    }

    record ListCompanySnapshot(UUID id, String name, String industry, CompanyType type, int employees, Instant createdDateTime) {
        public ListCompanySnapshot(Company c) {
            this(c.id(), c.name(), c.industry(), c.type(), c.employees(), c.createdDateTime());
        }
    }
}
