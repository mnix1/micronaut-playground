package com.example;

import com.example.repository.VehicleRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//IOC-test-3
@MicronautTest
class ReplaceImplementationTest {

    @Inject
    VehicleService vehicleService;

    @Bean
    @Requires(bean = ReplaceImplementationTest.class)
    @Replaces(bean = VehicleRepository.class)
    VehicleRepository mockVehicleRepository = () -> List.of("Test");

    @Test
    void returnsTest() {
        assertThat(vehicleService.vehicles()).containsExactly("Test");
    }
}
