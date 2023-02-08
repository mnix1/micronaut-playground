package com.example;

import com.example.repository.VehicleRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
class VehicleService {

    //can't be final
    @Inject
    private VehicleRepository vehicleRepository;

    //TODO check
    //    @Inject
    //    private SUVVehicleRepository suvVehicleRepository;

    @Inject
    private Optional<AdditionalSUVVehiclesService> additionalVehiclesService;

    List<String> vehicles() {
        List<String> vehicles = new ArrayList<>(vehicleRepository.vehicles());
        additionalVehiclesService.ifPresent(vehiclesService -> vehicles.add(vehiclesService.additionalVehicle()));
        return vehicles;
    }
}
