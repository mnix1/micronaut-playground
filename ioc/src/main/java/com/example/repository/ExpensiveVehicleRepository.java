package com.example.repository;

import java.util.List;

class ExpensiveVehicleRepository implements VehicleRepository {

    @Override
    public List<String> vehicles() {
        return List.of(
                "Ferrari",
                "Lamborghini"
        );
    }
}
