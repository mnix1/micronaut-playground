package com.example;

import io.micronaut.runtime.Micronaut;

class DynamoDBApplication {
    public static void main(String[] args) {
        Micronaut.run(DynamoDBApplication.class, args);
    }
}
