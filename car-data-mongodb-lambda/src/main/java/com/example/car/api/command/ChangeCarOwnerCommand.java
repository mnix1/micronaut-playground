package com.example.car.api.command;

import java.util.UUID;

public class ChangeCarOwnerCommand {
    public final UUID id;
    public final String owner;

    public ChangeCarOwnerCommand(UUID id, String owner) {
        this.id = id;
        this.owner = owner;
    }
}
