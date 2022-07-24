package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.retry.annotation.CircuitBreaker;
import io.micronaut.retry.annotation.Retryable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CircuitBreakerTest {
    private final static Logger LOG = LoggerFactory.getLogger(CircuitBreakerExample.class);
    CircuitBreakerExample example;
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = ApplicationContext.run();
        example = applicationContext.getBean(CircuitBreakerExample.class);
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }

    @Test
    void retries() {
        Assertions.assertThrows(RuntimeException.class, () -> example.valueRetryable());
        LOG.info("second try {}", Instant.now());
        assertThat(example.valueRetryable()).isEqualTo("4");
    }

    @Test
    void circuitBreakerOpens() {
        Assertions.assertThrows(RuntimeException.class, () -> example.valueCircuitBreaker());
        LOG.info("second try {}", Instant.now());
        Assertions.assertThrows(RuntimeException.class, () -> example.valueCircuitBreaker());
    }

    @Test
    void circuitBreakerOpensAndCloses() throws InterruptedException {
        Assertions.assertThrows(RuntimeException.class, () -> example.valueCircuitBreaker());
        //TODO try lower sleep values
        Thread.sleep(1001);
        LOG.info("second try {}", Instant.now());
        assertThat(example.valueCircuitBreaker()).isEqualTo("4");
    }

    @Prototype
    static class CircuitBreakerExample {
        int invokeCounter = 0;

        @CircuitBreaker(attempts = "1", delay = "100ms", reset = "1s")
        String valueCircuitBreaker() {
            LOG.info("valueCircuitBreaker invoked {} {}", invokeCounter, Instant.now());
            if (invokeCounter++ < 3) {
                throw new RuntimeException("Timeout");
            }
            return invokeCounter + "";
        }

        @Retryable(attempts = "1", delay = "100ms")
        String valueRetryable() {
            LOG.info("valueRetryable invoked {} {}", invokeCounter, Instant.now());
            if (invokeCounter++ < 3) {
                throw new RuntimeException("Timeout");
            }
            return invokeCounter + "";
        }
    }
}
