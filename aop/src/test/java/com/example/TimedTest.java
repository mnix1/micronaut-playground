package com.example;


import io.micronaut.context.ApplicationContext;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimedTest {
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = ApplicationContext.run();
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }

    @Test
    void logsExecution() throws InterruptedException {
        applicationContext.getBean(TimedExample.class).slowMethod();
    }

    @Test
    void logsExecutionFromInside() throws InterruptedException {
        applicationContext.getBean(TimedExample.class).otherMethod();
    }

    @Singleton
    static class TimedExample {
        //TODO change access modifier to private
        @Timed
        void slowMethod() throws InterruptedException {
            Thread.sleep(1000);
        }

        private void otherMethod() throws InterruptedException {
            slowMethod();
        }
    }
}
