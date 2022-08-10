package com.example.component;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

interface BookRepository {
    Book get(UUID id);

    List<Book> list();

    Book save(Book book);

    Book update(Book book);

    @Singleton
    @Context
    class DefaultBookRepository implements BookRepository {
        private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

        DefaultBookRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
            this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        }

        @PostConstruct
        void init() {
            DynamoDbTable<BookRecord> table = table();
            try {
                table.deleteTable();
            } catch (ResourceNotFoundException e) {
                //ignored
            }
            table.createTable();
        }

        @Override
        public Book get(UUID id) {
            return table().getItem(Key.builder().partitionValue(id.toString()).build()).toDomain();
        }

        @Override
        public List<Book> list() {
            return table().scan().items().stream().map(BookRecord::toDomain).toList();
        }

        @Override
        public Book save(Book book) {
            table().putItem(new BookRecord(book));
            return book;
        }

        @Override
        public Book update(Book book) {
            return table().updateItem(new BookRecord(book)).toDomain();
        }

        private DynamoDbTable<BookRecord> table() {
            return dynamoDbEnhancedClient.table("book", TableSchema.fromBean(BookRecord.class));
        }
    }

    @DynamoDbBean
    class BookRecord {
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
}
