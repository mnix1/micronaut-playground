package com.example.car.api.command;

import com.example.car.api.CarProducer;

public record CreateCarCommand(String model, CarProducer producer, int productionYear) {
}
