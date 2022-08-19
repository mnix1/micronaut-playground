package com.example.book.view;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.List;
import java.util.UUID;

@MappedEntity(value = "book")
public class MongoBookViewRecord implements BookViewRecord {
    @Id
    private String id;
    private String name;
    private Integer order;
    private boolean available;
    private Integer pages;
    private List<String> comments;

    public MongoBookViewRecord() {
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

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

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

    public List<String> getComments() {
        return comments;
    }

    @Override
    public UUID id() {
        return UUID.fromString(id);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Integer order() {
        return order;
    }

    @Override
    public boolean available() {
        return available;
    }

    @Override
    public Integer pages() {
        return pages;
    }

    @Override
    public List<String> comments() {
        return comments;
    }
}
