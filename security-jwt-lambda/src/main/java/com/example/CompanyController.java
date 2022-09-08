package com.example;

import com.github.javafaker.Faker;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller("/companies")
class CompanyController {

    private final List<Company> companies = new ArrayList<>();

    @Get
    List<Company> list() {
        return companies;
    }

    @Get("/{id}")
    Optional<Company> get(UUID id) {
        return companies.stream().filter(c -> c.id().equals(id)).findAny();
    }

    @Get("findByName")
    List<Company> findByName(String namePrefix, Optional<String> nameSuffix) {
        return companies
            .stream()
            .filter(c -> c.name().startsWith(namePrefix))
            .filter(c -> nameSuffix.filter(s -> !c.name().endsWith(s)).isEmpty())
            .collect(Collectors.toList());
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

    @Status(HttpStatus.NO_CONTENT)
    @Delete
    void delete() {
        companies.clear();
    }

    static class CompanyRequestBody {

        String name;
        String industry;
        int employees;
        Instant createdDateTime;
        List<Facility> facilities;

        public String getName() {
            return name;
        }

        public String getIndustry() {
            return industry;
        }

        public int getEmployees() {
            return employees;
        }

        public Instant getCreatedDateTime() {
            return createdDateTime;
        }

        public List<Facility> getFacilities() {
            return facilities;
        }

        CompanyRequestBody(String name, String industry, int employees, Instant createdDateTime, List<Facility> facilities) {
            this.name = name;
            this.industry = industry;
            this.employees = employees;
            this.createdDateTime = createdDateTime;
            this.facilities = facilities;
        }

        Company toCompany(UUID id) {
            return new Company(id, name, industry, employees, createdDateTime, facilities);
        }
    }

    private Company generateRandomCompany() {
        Faker faker = new Faker(Locale.forLanguageTag("PL"));
        return new Company(
            UUID.randomUUID(),
            faker.company().name(),
            faker.company().industry(),
            faker.number().numberBetween(2, 2000),
            faker.date().birthday().toInstant(),
            IntStream
                .rangeClosed(1, faker.number().numberBetween(1, 10))
                .boxed()
                .map(i -> new Facility(faker.country().name(), faker.address().cityName()))
                .collect(Collectors.toList())
        );
    }
}
