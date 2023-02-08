package com.example;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

record Company(UUID id, String name, String industry, CompanyType type, int employees, Instant createdDateTime, List<Facility> facilities) {}
