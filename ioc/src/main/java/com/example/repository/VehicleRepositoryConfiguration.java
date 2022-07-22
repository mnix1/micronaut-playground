package com.example.repository;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;

@Factory
class VehicleRepositoryConfiguration {
    @Bean
    @Primary
    VehicleRepository expensiveVehicleRepository() {
        return new ExpensiveVehicleRepository();
    }

    @Bean
    VehicleRepository cheapVehicleRepository() {
        return new CheapVehicleRepository();
    }
}
