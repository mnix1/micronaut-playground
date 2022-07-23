package com.example.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SUVVehicleRepository implements VehicleRepository {
    private final static Logger LOG = LoggerFactory.getLogger(SUVVehicleRepository.class);

    @Override
    public List<String> vehicles() {
        return List.of(
                "Toyota Rav4"
        );
    }
}
