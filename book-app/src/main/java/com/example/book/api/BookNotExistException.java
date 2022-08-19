package com.example.book.api;

import java.util.UUID;

public class BookNotExistException extends RuntimeException {
    public BookNotExistException(UUID id) {
        super("Book does not exist " + id);
    }
}
