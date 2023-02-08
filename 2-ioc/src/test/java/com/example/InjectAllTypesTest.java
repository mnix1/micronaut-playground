package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//IOC-test-4
@MicronautTest
class InjectAllTypesTest {

    @Inject
    Optional<Engine> engine;

    @Inject
    Optional<CarPart> carPart;

    @Inject
    Optional<CarEngine> carEngine;

    @Test
    void injectsAll() {
        assertThat(engine).withFailMessage("engine not exist").isPresent();
        assertThat(carEngine).withFailMessage("carEngine not exist").isPresent();
        assertThat(carPart).withFailMessage("carPart not exist").isPresent();
    }

    interface Engine {
        String start();
    }

    interface CarPart {
        String name();
    }

    record CarEngine() implements Engine, CarPart {
        @Override
        public String start() {
            return "Car engine started";
        }

        @Override
        public String name() {
            return "Car engine";
        }
    }

    @Singleton
    //TODO change CarEngine to Engine
    //TODO change @Singleton to @Prototype
    CarEngine engine() {
        return new CarEngine();
    }
}
