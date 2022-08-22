package com.example.car.api;

import java.util.UUID;

public record CarSnapshot(UUID id, String model, CarProducer producer, int productionYear, String owner) {}
