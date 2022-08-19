package com.example.car.component.http;


import com.example.car.api.CarFacade;
import com.example.car.api.CarSnapshot;
import com.example.car.api.command.ChangeCarOwnerCommand;
import com.example.car.api.command.CreateCarCommand;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.Post;

import java.util.List;
import java.util.UUID;

@Controller("/cars")
class CarController {
    private final CarFacade facade;

    CarController(CarFacade facade) {
        this.facade = facade;
    }

    @Post
    UUID create(CreateCarCommand requestBody) {
        return facade.create(requestBody);
    }

    @Patch("{id}/changeOrder")
    void changeOrder(UUID id, ChangeCarRequestBody requestBody) {
        facade.changeOwner(new ChangeCarOwnerCommand(id, requestBody.owner));
    }

    @Get
    List<CarSnapshot> list() {
        return facade.list();
    }

    static class ChangeCarRequestBody {
        public final String owner;

        public ChangeCarRequestBody(String owner) {
            this.owner = owner;
        }
    }
}
