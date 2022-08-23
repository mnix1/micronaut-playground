package com.example.book.component;

import com.example.book.view.BookView;

import java.util.List;
import java.util.UUID;

public record TestBookViewRecord(UUID id, String name, Integer order, boolean available, Integer pages, List<String> comments)
    implements BookView {
    public TestBookViewRecord(Book book) {
        this(book.id, book.name, book.order, book.available, book.pages, book.comments);
    }
}
