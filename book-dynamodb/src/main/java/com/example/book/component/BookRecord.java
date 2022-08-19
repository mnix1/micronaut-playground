package com.example.book.component;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
public class BookRecord {
    private UUID id;
    private String name;
    private Integer order;
    private boolean available;
    private Integer pages;
    private Instant createdTimestamp;
    private Instant updatedTimestamp;
    private List<String> comments;

    public BookRecord() {
    }

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDbAttribute("order")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDbAttribute("available")
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @DynamoDbAttribute("pages")
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @DynamoDbAttribute("createdTimestamp")
    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @DynamoDbAttribute("updatedTimestamp")
    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @DynamoDbAttribute("comments")
    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    Book toDomain() {
        return new Book(id, name, order, available, pages, createdTimestamp, updatedTimestamp, comments);
    }

    BookRecord(Book book) {
        this.id = book.id;
        this.name = book.name;
        this.order = book.order;
        this.available = book.available;
        this.pages = book.pages;
        this.createdTimestamp = book.createdTimestamp;
        this.updatedTimestamp = book.updatedTimestamp;
        this.comments = book.comments;
    }
}
