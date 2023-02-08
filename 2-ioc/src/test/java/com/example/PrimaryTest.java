package com.example;

import io.micronaut.context.annotation.Primary;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//IOC-test-2
@MicronautTest
class PrimaryTest {

    @Inject
    Engine engine;

    @Test
    void injectsPrimary() {
        assertThat(engine).isInstanceOf(V8Engine.class);
        assertThat(engine.start()).isEqualTo("Starting V8");
    }

    interface Engine {
        String start();
    }

    @Singleton
    static class V6Engine implements Engine {

        @Override
        public String start() {
            return "Starting V6";
        }
    }

    @Singleton
    //TODO remove @Primary and use @Any (?where)
    @Primary
    static class V8Engine implements Engine {

        @Override
        public String start() {
            return "Starting V8";
        }
    }
}
