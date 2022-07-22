package com.example;

import java.util.List;

class InMemoryVehicleRepository implements VehicleRepository {

    @Override
    public List<String> vehicles() {
        return List.of(
                "Ferrari",
                "Lambo",
                "Fiat"
        );
    }
}
