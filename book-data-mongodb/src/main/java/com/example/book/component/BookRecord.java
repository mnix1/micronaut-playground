package com.example.book.component;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@MappedEntity(value = "book")
public class BookRecord {

    @Id
    private String id;

    private String name;
    private Integer order;
    private boolean available;
    private Integer pages;
    private Instant createdTimestamp;
    private Instant updatedTimestamp;
    private List<String> comments;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public boolean isAvailable() {
        return available;
    }

    public Integer getPages() {
        return pages;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public void setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public BookRecord() {}

    Book toDomain() {
        return new Book(UUID.fromString(id), name, order, available, pages, createdTimestamp, updatedTimestamp, comments);
    }

    BookRecord(Book book) {
        this.id = book.id.toString();
        this.name = book.name;
        this.order = book.order;
        this.available = book.available;
        this.pages = book.pages;
        this.createdTimestamp = book.createdTimestamp;
        this.updatedTimestamp = book.updatedTimestamp;
        this.comments = book.comments;
    }
}
