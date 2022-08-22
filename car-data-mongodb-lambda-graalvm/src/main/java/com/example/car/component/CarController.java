package com.example.car.component;

import com.example.car.api.CarFacade;
import com.example.car.api.CarProducer;
import com.example.car.api.CarSnapshot;
import com.example.car.api.command.ChangeCarOwnerCommand;
import com.example.car.api.command.CreateCarCommand;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller("/cars")
class CarController {

    private final CarFacade facade;

    CarController(CarFacade facade) {
        this.facade = facade;
    }

    @Post
    @Status(HttpStatus.CREATED)
    UUID create(@Body CreateCarRequestBody requestBody) {
        return facade.create(requestBody.toCommand());
    }

    @Patch("{id}/changeOwner")
    void changeOwner(@PathVariable UUID id, @Body ChangeCarOwnerRequestBody requestBody) {
        facade.changeOwner(new ChangeCarOwnerCommand(id, requestBody.owner));
    }

    @Get
    List<CarSnapshot> list() {
        return facade.list();
    }

    @JsonDeserialize
    static class CreateCarRequestBody {

        private final String model;
        private final CarProducer producer;
        private final int productionYear;

        CreateCarRequestBody(String model, CarProducer producer, int productionYear) {
            this.model = model;
            this.producer = producer;
            this.productionYear = productionYear;
        }

        CreateCarCommand toCommand() {
            return new CreateCarCommand(model, producer, productionYear);
        }
    }

    @JsonDeserialize
    static class ChangeCarOwnerRequestBody {

        private final String owner;

        ChangeCarOwnerRequestBody(String owner) {
            this.owner = owner;
        }
    }
}
