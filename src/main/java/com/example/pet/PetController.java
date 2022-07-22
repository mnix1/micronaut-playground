package com.example.pet;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.time.Instant;
import java.util.List;

@Controller("/pets")
class PetController {
    @Get
    List<Pet> list() {
        return List.of(
                new Pet("Reksio", PetType.DOG, Instant.parse("2010-02-04T13:02:23Z"))
        );
    }
}
