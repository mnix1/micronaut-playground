package com.example;

import io.micronaut.context.annotation.Primary;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class PrimaryTest {
    @Inject
    Vehicle vehicle;

    @Test
    void injectsPrimary() {
        assertThat(vehicle.engine).isInstanceOf(V8Engine.class);
        assertThat(vehicle.start()).isEqualTo("Starting V8");
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

    @Singleton
    static class Vehicle {
        private final Engine engine;

        Vehicle(Engine engine) {
            this.engine = engine;
        }

        String start() {
            return engine.start();
        }
    }
}
