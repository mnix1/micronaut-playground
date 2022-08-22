package com.example.audit;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

@MongoRepository
public interface AuditRepository extends CrudRepository<AuditEntity, String> {}
