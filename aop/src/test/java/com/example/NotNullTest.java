package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class NotNullTest {

    @Inject
    NotNullExample notNullExample;

    @Test
    void forbidsNullArgument() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                notNullExample.doWork(null);
            }
        );
        notNullExample.doWork("clean");
    }

    @Singleton
    static class NotNullExample {

        @NotNull
        void doWork(String taskName) {
            System.out.println("Doing job: " + taskName);
        }
    }
}
