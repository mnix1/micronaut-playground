package com.example.view;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@MongoRepository
interface BookViewRepository extends CrudRepository<BookViewRecord, String> {
    List<BookViewRecord> findByAvailable(boolean available);
}
