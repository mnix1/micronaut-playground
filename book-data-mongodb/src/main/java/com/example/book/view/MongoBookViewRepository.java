package com.example.book.view;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@MongoRepository
interface MongoBookViewRepository extends CrudRepository<MongoBookViewRecord, String>, BookViewRepository {
    List<MongoBookViewRecord> findByAvailable(boolean available);

    @Override
    default List<? extends BookViewRecord> filter(Boolean available) {
        return findByAvailable(available);
    }

    @Override
    default List<? extends BookViewRecord> list() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }
}
