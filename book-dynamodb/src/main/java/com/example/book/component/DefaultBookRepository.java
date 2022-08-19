package com.example.book.component;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

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
    public Optional<Book> get(UUID id) {
        BookRecord item = table().getItem(Key.builder().partitionValue(id.toString()).build());
        return ofNullable(item).map(BookRecord::toDomain);
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
