package com.example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JsonSerialize
@JsonDeserialize
class Company {

    private final UUID id;
    private final String name;
    private final String industry;
    private final int employees;
    private final Instant createdDateTime;
    private final List<Facility> facilities;

    Company(UUID id, String name, String industry, int employees, Instant createdDateTime, List<Facility> facilities) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.employees = employees;
        this.createdDateTime = createdDateTime;
        this.facilities = facilities;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String industry() {
        return industry;
    }

    public int employees() {
        return employees;
    }

    public Instant createdDateTime() {
        return createdDateTime;
    }

    public List<Facility> facilities() {
        return facilities;
    }

    public UUID getId() {
        return id;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Company) obj;
        return (
            Objects.equals(this.id, that.id) &&
            Objects.equals(this.name, that.name) &&
            Objects.equals(this.industry, that.industry) &&
            this.employees == that.employees &&
            Objects.equals(this.createdDateTime, that.createdDateTime) &&
            Objects.equals(this.facilities, that.facilities)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, industry, employees, createdDateTime, facilities);
    }

    @Override
    public String toString() {
        return (
            "Company[" +
            "id=" +
            id +
            ", " +
            "name=" +
            name +
            ", " +
            "industry=" +
            industry +
            ", " +
            "employees=" +
            employees +
            ", " +
            "createdDateTime=" +
            createdDateTime +
            ", " +
            "facilities=" +
            facilities +
            ']'
        );
    }
}
