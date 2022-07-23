package com.example;

import io.micronaut.context.annotation.EachBean;

@EachBean(Car.class)
record Driver(Car car) {
}
