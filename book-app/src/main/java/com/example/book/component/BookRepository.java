package com.example.book.component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface BookRepository {
    Optional<Book> get(UUID id);

    List<Book> list();

    Book save(Book book);

    Book update(Book book);

}
