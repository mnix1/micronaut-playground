package com.example.book.component;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@MongoRepository
interface MongoBookRepository extends CrudRepository<BookRecord, String>, BookRepository {
    @Override
    default Optional<Book> get(UUID id) {
        return findById(id.toString()).map(BookRecord::toDomain);
    }

    @Override
    default Book save(Book book) {
        return save(new BookRecord(book)).toDomain();
    }

    @Override
    default Book update(Book book) {
        return update(new BookRecord(book)).toDomain();
    }

    @Override
    default List<Book> list() {
        return StreamSupport.stream(findAll().spliterator(), false).map(BookRecord::toDomain).toList();
    }
}
