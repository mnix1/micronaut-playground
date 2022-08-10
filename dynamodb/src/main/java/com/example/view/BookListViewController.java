package com.example.view;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.Optional;


@Controller("/books")
class BookListViewController {
    private final BookViewRepository repository;

    BookListViewController(BookViewRepository repository) {
        this.repository = repository;
    }

    @Get
    List<BookViewRecord> list(Optional<Boolean> available) {
        return repository.list();
    }
}
