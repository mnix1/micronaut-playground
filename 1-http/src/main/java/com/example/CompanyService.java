package com.example;

import com.github.javafaker.Faker;
import jakarta.inject.Singleton;

import java.util.*;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Singleton
class CompanyService {
    private final List<Company> companies = new ArrayList<>();

    Company createRandom() {
        Company company = generateRandomCompany();
        companies.add(company);
        return company;
    }

    void create(Company company){
        companies.add(company);
    }

    List<Company> find(Optional<Integer> employeesFrom, Optional<Integer> employeesTo) {
        return companies
            .stream()
            .filter(c -> employeesFrom.filter(i -> c.employees() < i).isEmpty())
            .filter(c -> employeesTo.filter(i -> c.employees() > i).isEmpty())
            .toList();
    }

    List<Company> findByName(String namePrefix, Optional<String> nameSuffix) {
        return companies
            .stream()
            .filter(c -> c.name().startsWith(namePrefix))
            .filter(c -> nameSuffix.filter(s -> !c.name().endsWith(s)).isEmpty())
            .toList();
    }

    Optional<Company> get(UUID id) {
        return companies.stream().filter(c -> c.id().equals(id)).findAny();
    }

    void delete(UUID id){
        companies.removeIf(c -> c.id().equals(id));
    }

    void deleteAll() {
        companies.clear();
    }

    protected Company generateRandomCompany() {
        Faker faker = new Faker(Locale.forLanguageTag("PL"));
        return new Company(
            UUID.randomUUID(),
            faker.company().name(),
            faker.company().industry(),
            CompanyType.values()[nextInt(0, CompanyType.values().length)],
            faker.number().numberBetween(2, 2000),
            faker.date().birthday().toInstant(),
            IntStream
                .rangeClosed(1, faker.number().numberBetween(1, 4))
                .boxed()
                .map(i -> new Facility(faker.country().name(), faker.address().cityName()))
                .toList()
        );
    }

}
