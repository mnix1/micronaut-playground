package com.example.book.component;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
@Requires(missingBeans = BookRepository.class)
class InMemoryBookRepository implements BookRepository {

    Map<UUID, Book> books = new HashMap<>();

    @Override
    public Optional<Book> get(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> list() {
        return books.values().stream().toList();
    }

    @Override
    public Book save(Book book) {
        if (get(book.id).isPresent()) {
            throw new RuntimeException("Already exists");
        }
        books.put(book.id, book);
        return book;
    }

    @Override
    public Book update(Book book) {
        books.put(book.id, book);
        return book;
    }
}
