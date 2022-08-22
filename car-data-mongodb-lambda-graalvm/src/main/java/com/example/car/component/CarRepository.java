package com.example.car.component;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

@MongoRepository
interface CarRepository extends CrudRepository<CarEntity, String> {
}
