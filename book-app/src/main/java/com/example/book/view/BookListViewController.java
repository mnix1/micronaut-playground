package com.example.book.view;

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
    List<? extends BookView> list(Optional<Boolean> available) {
        if (available.isPresent()) {
            return repository.filter(available.get());
        }
        return repository.list();
    }
}
