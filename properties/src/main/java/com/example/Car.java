package com.example;

import io.micronaut.context.annotation.EachProperty;

import java.time.Instant;

@EachProperty(value = "cars", list = true)
record Car(String name, int mass, CarType type, Instant produced) {
}
