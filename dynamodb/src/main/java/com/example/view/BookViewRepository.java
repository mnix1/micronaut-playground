package com.example.view;

import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

@Singleton
class BookViewRepository {
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    BookViewRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    List<BookViewRecord> list() {
        return table().scan().items().stream().toList();
    }

    List<BookViewRecord> filter(Boolean available) {
        return table().scan().items().stream().filter(bookViewRecord -> available.equals(bookViewRecord.isAvailable())).toList();
    }

    private DynamoDbTable<BookViewRecord> table() {
        return dynamoDbEnhancedClient.table("book", TableSchema.fromBean(BookViewRecord.class));
    }
}
