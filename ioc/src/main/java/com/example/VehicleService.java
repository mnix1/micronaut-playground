package com.example;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
class VehicleService {
    //can't be final
    @Inject
    private VehicleRepository vehicleRepository;

    List<String> vehicles() {
        return vehicleRepository.vehicles();
    }
}