package com.example.book.component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

class Book {

    final UUID id;
    final String name;
    Integer order;
    boolean available;
    final Integer pages;
    final Instant createdTimestamp;
    Instant updatedTimestamp;
    List<String> comments;

    Book(
        UUID id,
        String name,
        Integer order,
        boolean available,
        Integer pages,
        Instant createdTimestamp,
        Instant updatedTimestamp,
        List<String> comments
    ) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.available = available;
        this.pages = pages;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
        this.comments = comments;
    }

    static Book create(String name, Integer pages, Integer booksCount) {
        return new Book(UUID.randomUUID(), name, booksCount, false, pages, Instant.now(), Instant.now(), List.of());
    }

    void addComment(String comment) {
        comments = new ArrayList<>(ofNullable(comments).orElse(List.of()));
        comments.add(comment);
        updatedTimestamp = Instant.now();
    }

    void changeOrder(Integer order) {
        this.order = order;
    }

    void unavailable() {
        this.available = false;
    }

    void available() {
        this.available = true;
    }
}
