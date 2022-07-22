package com.example;

import com.github.javafaker.Faker;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

@Controller("/companies")
class CompanyController {
    private final List<Company> companies = new ArrayList<>();

    @Get
    List<ListCompanySnapshot> find(Optional<Integer> employeesFrom, Optional<Integer> employeesTo) {
        return companies.stream()
                .filter(c -> employeesFrom.filter(i -> c.employees() < i).isEmpty())
                .filter(c -> employeesTo.filter(i -> c.employees() > i).isEmpty())
                .map(c -> new ListCompanySnapshot(c.id(), c.name(), c.industry(), c.employees(), c.createdDateTime().toString()))
                .toList();
    }

    @Get("findByName")
    List<ListCompanySnapshot> findByName(String namePrefix, Optional<String> nameSuffix) {
        return companies.stream()
                .filter(c -> c.name().startsWith(namePrefix))
                .filter(c -> nameSuffix.filter(s -> !c.name().endsWith(s)).isEmpty())
                .map(c -> new ListCompanySnapshot(c.id(), c.name(), c.industry(), c.employees(), c.createdDateTime().toString()))
                .toList();
    }

    @Get("/{id}")
    Optional<Company> get(UUID id) {
        return companies.stream().filter(c -> c.id().equals(id)).findAny();
    }

    @Post("/random")
    HttpResponse<Company> createRandom() {
        Company company = generateRandomCompany();
        companies.add(company);
        return HttpResponse.created(company).header("RANDOM", "true");
    }

    @Post("/random/{count}")
    HttpResponse createManyRandom(int count) {
        for (int i = 0; i < count; i++) {
            companies.add(generateRandomCompany());
        }
        return HttpResponse.status(HttpStatus.CREATED).header("RANDOM", "true").header("COUNT", count + "");
    }

    @Post
    HttpResponse<Company> create(CompanyRequestBody requestBody) {
        Company company = requestBody.toCompany(UUID.randomUUID());
        companies.add(company);
        return HttpResponse.created(company);
    }

    @Delete("/{id}")
    HttpResponse<Void> delete(UUID id) {
        companies.removeIf(c -> c.id().equals(id));
        return HttpResponse.noContent();
    }

    @Delete
    HttpResponse<Void> delete() {
        companies.clear();
        return HttpResponse.noContent();
    }

    record CompanyRequestBody(
            String name,
            String industry,
            int employees,
            Instant createdDateTime,
            List<Facility> facilities
    ) {

        Company toCompany(UUID id) {
            return new Company(id, name, industry, employees, createdDateTime, facilities);
        }
    }

    record ListCompanySnapshot(
            UUID id,
            String name,
            String industry,
            int employees,
            String createdDateTime
    ) {

    }

    private Company generateRandomCompany() {
        Faker faker = new Faker(Locale.forLanguageTag("PL"));
        return new Company(
                UUID.randomUUID(),
                faker.company().name(),
                faker.company().industry(),
                faker.number().numberBetween(2, 2000),
                faker.date().birthday().toInstant(),
                IntStream.rangeClosed(1, faker.number().numberBetween(1, 10)).boxed().map(i -> new Facility(
                        faker.country().name(),
                        faker.address().cityName()
                )).toList()
        );
    }
}
