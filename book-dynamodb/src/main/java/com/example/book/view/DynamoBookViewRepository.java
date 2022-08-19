package com.example.book.view;

import jakarta.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

@Singleton
class DynamoBookViewRepository implements BookViewRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    DynamoBookViewRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    public List<? extends BookViewRecord> list() {
        return table().scan().items().stream().toList();
    }

    public List<? extends BookViewRecord> filter(Boolean available) {
        return table().scan().items().stream().filter(bookViewRecord -> available.equals(bookViewRecord.isAvailable())).toList();
    }

    private DynamoDbTable<DynamoBookViewRecord> table() {
        return dynamoDbEnhancedClient.table("book", TableSchema.fromBean(DynamoBookViewRecord.class));
    }
}
