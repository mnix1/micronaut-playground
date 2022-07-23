package com.example;

import io.micronaut.context.annotation.EachBean;

@EachBean(Car.class)
class Driver {
    private final Car car;

    Driver(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}
