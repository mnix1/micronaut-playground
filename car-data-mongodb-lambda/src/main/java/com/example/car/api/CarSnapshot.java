package com.example.car.api;

import java.util.Objects;
import java.util.UUID;

public class CarSnapshot {

    public final UUID id;
    public final String model;
    public final CarProducer producer;
    public final int productionYear;
    public final String owner;

    public CarSnapshot(UUID id, String model, CarProducer producer, int productionYear, String owner) {
        this.id = id;
        this.model = model;
        this.producer = producer;
        this.productionYear = productionYear;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarSnapshot that = (CarSnapshot) o;
        return (
            productionYear == that.productionYear &&
            id.equals(that.id) &&
            model.equals(that.model) &&
            producer == that.producer &&
            Objects.equals(owner, that.owner)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, producer, productionYear, owner);
    }
}
