package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class QualifiersTest {
    @Inject
    FastVehicle fastVehicle;

    @Inject
    SlowVehicle slowVehicle;

    @Test
    void named() {
        assertThat(fastVehicle.engine).isInstanceOf(V8Engine.class);
        assertThat(fastVehicle.start()).isEqualTo("Starting V8");
    }

    @Test
    void customAnnotation() {
        assertThat(slowVehicle.engine).isInstanceOf(V6Engine.class);
        assertThat(slowVehicle.start()).isEqualTo("Starting V6");
    }

    interface Engine {
        String start();
    }

    @Singleton
    @Named("slowEngine")
    static class V6Engine implements Engine {
        @Override
        public String start() {
            return "Starting V6";
        }
    }

    @Singleton
    static class V8Engine implements Engine {
        @Override
        public String start() {
            return "Starting V8";
        }
    }

    @Singleton
    static class FastVehicle {
        private final Engine engine;

        //try to remove annotation
        //try to change annotation value to v8Engin
        //try to change annotation value to v8Engine
        //try to change annotation value to v6
        //try to change annotation value to slow
        //try to change annotation value to slowEng
        //try to change annotation value to slowEngine
        FastVehicle(@Named("v8") Engine engine) {
            this.engine = engine;
        }

        String start() {
            return engine.start();
        }
    }

    @Singleton
    static class SlowVehicle {
        private final Engine engine;

        SlowVehicle(@V6 Engine engine) {
            this.engine = engine;
        }

        String start() {
            return engine.start();
        }
    }


    @Named("slowEngine")
    @Retention(RUNTIME)
    public @interface V6 {
    }
}
