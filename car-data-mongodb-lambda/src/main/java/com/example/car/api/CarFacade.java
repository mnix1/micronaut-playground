package com.example.car.api;

import com.example.car.api.command.ChangeCarOwnerCommand;
import com.example.car.api.command.CreateCarCommand;

import java.util.List;
import java.util.UUID;

public interface CarFacade {
    UUID create(CreateCarCommand command);

    void changeOwner(ChangeCarOwnerCommand command);

    List<CarSnapshot> list();
}
