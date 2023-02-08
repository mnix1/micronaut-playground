package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class EachPropertyTest {

    @Inject
    List<Car> cars;

    @Test
    void createsMultipleBeans() {
        //TODO change containsExactlyInAnyOrder to containsExactly
        //TODO check InstantTypeConverter.java
        assertThat(cars)
            .containsExactlyInAnyOrder(
                new Car("Toyota Rav4", 2010, CarType.SUV, Instant.parse("2010-10-02T00:04:05Z")),
                new Car("Volvo XC40", 1810, CarType.SUV, Instant.parse("2013-02-04T10:05:02Z")),
                new Car("Audi A4", 1410, CarType.SEDAN, Instant.parse("2019-12-09T14:07:02Z")),
                new Car("Hyundai i20", 1104, CarType.HATCHBACK, Instant.parse("2015-01-04T09:34:01Z"))
            );
    }
}
