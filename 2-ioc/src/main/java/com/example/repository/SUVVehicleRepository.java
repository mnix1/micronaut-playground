package com.example.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SUVVehicleRepository implements VehicleRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SUVVehicleRepository.class);

    public SUVVehicleRepository() {
        LOG.info("SUVVehicleRepository constructor");
    }

    @PostConstruct
    void init() {
        LOG.info("SUVVehicleRepository init");
    }

    @PreDestroy
    void destroy() {
        LOG.info("SUVVehicleRepository destroy");
    }

    @Override
    public List<String> vehicles() {
        return List.of("Toyota Rav4");
    }
}
