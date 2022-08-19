package com.example.book.component;

import com.example.book.api.BookFacade;
import com.example.book.api.BookNotExistException;
import com.example.book.api.command.*;
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
        Book book = repository.get(command.bookId()).orElseThrow(() -> new BookNotExistException(command.bookId()));
        book.addComment(command.comment());
        repository.update(book);
    }

    @Override
    public void makeAvailable(MakeBookAvailableCommand command) {
        Book book = repository.get(command.bookId()).orElseThrow(() -> new BookNotExistException(command.bookId()));
        book.available();
        repository.update(book);
    }

    @Override
    public void makeUnavailable(MakeBookUnavailableCommand command) {
        Book book = repository.get(command.bookId()).orElseThrow(() -> new BookNotExistException(command.bookId()));
        book.unavailable();
        repository.update(book);
    }

    @Override
    public void increaseOrder(BookOrderIncreaseCommand command) {

    }

    @Override
    public void decreaseOrder(BookOrderDecreaseCommand command) {

    }
}
