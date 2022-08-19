package com.example.car.component;

import com.example.car.api.CarProducer;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.time.Instant;
import java.util.UUID;

@MappedEntity(value = "car")
class CarRecord {
    @Id
    String id;
    String model;
    CarProducer producer;
    int productionYear;
    String owner;
    Instant createdTimestamp;
    Instant updatedTimestamp;

    public CarRecord() {
    }

    CarRecord(Car car) {
        id = car.id.toString();
        model = car.model;
        producer = car.producer;
        productionYear = car.productionYear;
        owner = car.owner;
        createdTimestamp = Instant.now();
        updatedTimestamp = Instant.now();
    }

    Car toDomain() {
        return new Car(
                UUID.fromString(id), model, producer, productionYear, owner
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarProducer getProducer() {
        return producer;
    }

    public void setProducer(CarProducer producer) {
        this.producer = producer;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Instant getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
}
