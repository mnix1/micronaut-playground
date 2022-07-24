package com.example;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StubTest {

    @Test
    void returnsStubbedValue() {
        try (ApplicationContext applicationContext = ApplicationContext.run()) {
            StubExample stubExample = applicationContext.getBean(StubExample.class);
            assertThat(stubExample.getNumber()).isEqualTo(10);
            assertThat(stubExample.getDate()).isNull();
            assertThat(stubExample.getString()).isEqualTo("someString");
        }
    }

    @Stub
    interface StubExample {
        @Stub("10")
        int getNumber();
        LocalDateTime getDate();
        @Stub("someString")
        String getString();
    }
}
