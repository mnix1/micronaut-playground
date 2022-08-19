package com.example.car.api.command;

import com.example.car.api.CarProducer;

public class CreateCarCommand {
    public final String model;
    public final CarProducer producer;
    public final int productionYear;

    public CreateCarCommand(String model, CarProducer producer, int productionYear) {
        this.model = model;
        this.producer = producer;
        this.productionYear = productionYear;
    }
}
