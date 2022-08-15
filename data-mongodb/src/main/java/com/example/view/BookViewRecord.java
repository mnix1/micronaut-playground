package com.example.view;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.List;

@MappedEntity(value = "book")
public class BookViewRecord {
    @Id
    private String id;
    private String name;
    private Integer order;
    private boolean available;
    private Integer pages;
    private List<String> comments;

    public BookViewRecord() {
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
}
