package com.example.repository;

import java.util.List;

class CheapVehicleRepository implements VehicleRepository {

    @Override
    public List<String> vehicles() {
        return List.of("Fiat 126p");
    }
}
