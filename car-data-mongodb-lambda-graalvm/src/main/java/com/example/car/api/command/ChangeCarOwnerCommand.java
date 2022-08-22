package com.example.car.api.command;

import java.util.UUID;

public record ChangeCarOwnerCommand(UUID id, String owner) {
}
