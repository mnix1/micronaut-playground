package com.example;


import com.example.repository.SUVVehicleRepository;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Singleton
@Requires(bean = SUVVehicleRepository.class)
class AdditionalVehiclesService {

    String additionalVehicle() {
        return "Toyota Highlander";
    }
}
