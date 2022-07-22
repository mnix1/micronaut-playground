package com.example.repository;

import java.util.List;

public class SUVVehicleRepository implements VehicleRepository {

    @Override
    public List<String> vehicles() {
        return List.of(
                "Toyota Rav4"
        );
    }
}
