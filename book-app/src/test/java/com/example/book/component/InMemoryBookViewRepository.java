package com.example.book.component;

import com.example.book.view.BookView;
import com.example.book.view.BookViewRepository;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
@Requires(missingBeans = BookViewRepository.class)
class InMemoryBookViewRepository implements BookViewRepository {

    private final InMemoryBookRepository bookRepository;

    InMemoryBookViewRepository(InMemoryBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<? extends BookView> list() {
        return bookRepository.list().stream().map(TestBookViewRecord::new).toList();
    }

    @Override
    public List<? extends BookView> filter(boolean available) {
        return list().stream().filter(bookViewRecord -> bookViewRecord.available() == available).toList();
    }
}
