package com.example.component;

import com.example.api.BookFacade;
import com.example.api.command.*;
import jakarta.inject.Singleton;

import java.util.UUID;


@Singleton

class DefaultBookFacade implements BookFacade {
    private final BookRepository repository;

    DefaultBookFacade(BookRepository repository) {
        this.repository = repository;
    }
    @Override
    public UUID create(CreateBookCommand command) {
        int booksCount = repository.list().size();
        return repository.save(Book.create(command.name(), command.pages(), booksCount)).id;
    }

    @Override
    public void comment(CommentBookCommand command) {
        Book book = repository.get(command.bookId());
        book.addComment(command.comment());
        repository.update(book);
    }

    @Override
    public void makeAvailable(MakeBookAvailableCommand command) {

    }

    @Override
    public void makeUnavailable(MakeBookUnavailableCommand command) {

    }

    @Override
    public void changeOrder(ChangeBookOrderCommand command) {

    }
}
