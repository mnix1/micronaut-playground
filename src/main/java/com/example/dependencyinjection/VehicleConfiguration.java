package com.example.dependencyinjection;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

@Factory
class VehicleConfiguration {
    @Bean
    VehicleRepository vehicleRepository() {
        return new InMemoryVehicleRepository();
    }
}
