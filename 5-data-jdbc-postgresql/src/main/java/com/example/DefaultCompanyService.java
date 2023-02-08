package com.example;

import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

@Singleton
@Replaces(CompanyService.class)
@RequiredArgsConstructor
class DefaultCompanyService extends CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    Company createRandom() {
        Company company = generateRandomCompany();
        companyRepository.save(CompanyRecord.of(company));
        return company;
    }

    @Override
    void create(Company company) {
        companyRepository.save(CompanyRecord.of(company));
    }

    @Override
    List<Company> find(Optional<Integer> employeesFrom, Optional<Integer> employeesTo) {
        return companyRepository.findByEmployeesBetween(employeesFrom.orElse(MIN_VALUE), employeesTo.orElse(MAX_VALUE))
            .stream()
            .map(CompanyRecord::toDomain)
            .toList();
    }

    @Override
    List<Company> findByName(String namePrefix, Optional<String> nameSuffix) {
        return companyRepository.findByNameStartsWithAndNameEndsWith(namePrefix, nameSuffix.orElse(""))
            .stream()
            .map(CompanyRecord::toDomain)
            .toList();
    }

    @Override
    Optional<Company> get(UUID id) {
        return companyRepository.findById(id).map(CompanyRecord::toDomain);
    }

    @Override
    void delete(UUID id) {
        companyRepository.deleteById(id);
    }

    @Override
    void deleteAll() {
        companyRepository.deleteAll();
    }
}
