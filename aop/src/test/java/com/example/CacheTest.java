package com.example;

import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.context.ApplicationContext;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {

    @Test
    void caches() {
        Random random = new Random(10);

        try (ApplicationContext applicationContext = ApplicationContext.run()) {
            CacheTest.CacheExample example = applicationContext.getBean(CacheTest.CacheExample.class);
            int value = random.nextInt();
            for (int i = 0; i < 10; i++) {
                assertThat(example.slowMethod()).isEqualTo("value " + value);
            }
        }
    }

    @Singleton
    @CacheConfig("slowMethod")
    static class CacheExample {

        Random random = new Random(10);

        @Timed
        @Cacheable
        String slowMethod() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "value " + random.nextInt();
        }
    }
}
