package com.example;


import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class InjectValueTest {

    //TODO rename application-test.yml to application.yml
    @Property(name = "inject.value1")
    int value1;
    @Property(name = "inject.value2")
    int value2;
    @Property(name = "inject.value3", defaultValue = "5")
    int value3;

    @Test
    void injectsFromMainSource() {
        assertThat(value1).isEqualTo(10);
    }

    @Test
    void injectsFromTestSource() {
        assertThat(value2).isEqualTo(20);
    }

    @Test
    void injectsDefault() {
        assertThat(value3).isEqualTo(5);
    }
}
