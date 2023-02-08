package com.example;

import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.context.ApplicationContext;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

//AOP-test-7
class CacheTest {

    @Test
    void caches() {

        try (ApplicationContext applicationContext = ApplicationContext.run()) {
            CacheTest.CacheExample example = applicationContext.getBean(CacheTest.CacheExample.class);
            for (int i = 0; i < 10; i++) {
                assertThat(example.slowMethod()).isEqualTo("value 1");
            }
        }
    }

    @Singleton
    @CacheConfig("slowMethod")
    static class CacheExample {

        static int count = 0;

        //TODO try uncomment getOrder method
        @Timed
        @Cacheable
        String slowMethod() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "value " + ++count;
        }
    }
}
