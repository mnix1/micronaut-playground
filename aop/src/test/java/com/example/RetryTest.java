package com.example;

import io.micronaut.retry.annotation.Retryable;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class RetryTest {
    @Inject
    RetryExample example;

    @Test
    void test() {
        assertThat(example.value()).isEqualTo("4");
    }

    @Singleton
    static class RetryExample {
        int invokeCounter = 0;
        //TODO try changing @Retryable properties
        //TODO try uncomment getOrder method
        @Retryable
        @Timed
        String value() {
            otherMethod();
            if (invokeCounter++ < 3) {
                throw new RuntimeException("Timeout");
            }
            return invokeCounter + "";
        }

        @Timed
        void otherMethod() {
        }
    }
}
