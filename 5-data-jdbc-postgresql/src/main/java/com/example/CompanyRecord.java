package com.example;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedEntity(value = "company")
class CompanyRecord {
    @Id
    UUID id;
    String name;
    String industry;
    CompanyType type;
    int employees;
    Instant createdDateTime;
    @TypeDef(type = DataType.JSON)
    List<Facility> facilities;

    static CompanyRecord of(Company company) {
        return new CompanyRecord(
            company.id(),
            company.name(),
            company.industry(),
            company.type(),
            company.employees(),
            company.createdDateTime(),
            company.facilities()
        );
    }

    Company toDomain() {
        return new Company(
            id,
            name,
            industry,
            type,
            employees,
            createdDateTime,
            facilities
        );
    }
}
