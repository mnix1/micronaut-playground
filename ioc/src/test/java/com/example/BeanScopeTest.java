package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Bean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class BeanScopeTest {
    private final static Logger LOG = LoggerFactory.getLogger(BeanScopeTest.class);

    @Inject
    ApplicationContext applicationContext;

    @Test
    void test() {
        //when
        for (int i = 0; i < 100; i++) {
            applicationContext.getBean(SimpleSingleton.class);
            applicationContext.getBean(SimplePrototype.class);
        }
        //then
        assertThat(SimpleSingleton.count).isEqualTo(1);
        assertThat(SimplePrototype.count).isEqualTo(100);
    }

    @Singleton
    SimpleSingleton simpleSingleton() {
        return new SimpleSingleton();
    }

    @Bean
    SimplePrototype simplePrototype() {
        return new SimplePrototype();
    }

    static class SimpleSingleton {
        static int count = 0;

        SimpleSingleton() {
            LOG.info("SimpleSingleton initialized " + ++count);
        }
    }

    static class SimplePrototype {
        static int count = 0;

        SimplePrototype() {
            LOG.info("SimplePrototype initialized " + ++count);
        }
    }
}
