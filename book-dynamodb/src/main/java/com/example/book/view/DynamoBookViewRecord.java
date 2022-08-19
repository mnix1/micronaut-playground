package com.example.book.view;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;
import java.util.UUID;

@DynamoDbBean
public class DynamoBookViewRecord implements BookViewRecord {

    private UUID id;
    private String name;
    private Integer order;
    private boolean available;
    private Integer pages;
    private List<String> comments;

    public DynamoBookViewRecord() {}

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public UUID getId() {
        return id;
    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public UUID id() {
        return null;
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
