package com.example.car.api;

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
}
