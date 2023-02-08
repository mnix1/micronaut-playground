package com.example;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;
import java.util.UUID;

@JdbcRepository
interface CompanyRepository extends PageableRepository<CompanyRecord, UUID> {
    List<CompanyRecord> findByEmployeesBetween(int from, int to);
    List<CompanyRecord> findByNameStartsWithAndNameEndsWith(String namePrefix, String nameSuffix);
}
