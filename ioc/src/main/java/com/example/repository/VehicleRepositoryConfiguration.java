package com.example.repository;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Factory
class VehicleRepositoryConfiguration {
    @Singleton
    @Requires(missingBeans = VehicleRepository.class)
    VehicleRepository defaultVehicleRepository() {
        return new CheapVehicleRepository();
    }

    @Singleton
    @Requires(property = "vehicles.expensive", value = "true")
    VehicleRepository expensiveVehicleRepository() {
        return new ExpensiveVehicleRepository();
    }

    @Singleton
    @Requires(property = "vehicles.cheap", value = "true")
    VehicleRepository cheapVehicleRepository() {
        return new CheapVehicleRepository();
    }

    //TODO change @Singleton to @Prototype
    //TODO change VehicleRepository to SUVVehicleRepository
    @Singleton
    @Requires(property = "vehicles.suv", value = "true")
    VehicleRepository suvVehicleRepository() {
        return new SUVVehicleRepository();
    }
}
