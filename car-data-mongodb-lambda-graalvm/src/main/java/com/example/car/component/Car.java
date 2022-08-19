package com.example.car.component;

import com.example.car.api.CarProducer;
import com.example.car.api.CarSnapshot;

import java.util.UUID;

class Car {
    final UUID id;
    final String model;
    final CarProducer producer;
    final int productionYear;
    String owner;

    Car(UUID id, String model, CarProducer producer, int productionYear, String owner) {
        this.id = id;
        this.model = model;
        this.producer = producer;
        this.productionYear = productionYear;
        this.owner = owner;
    }


    public CarSnapshot toSnapshot() {
        return new CarSnapshot(id, model, producer, productionYear, owner);
    }

    public void changeOwner(String owner) {
        this.owner = owner;
    }
}
