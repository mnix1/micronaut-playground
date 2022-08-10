package com.example.api.command;

import java.util.UUID;

public record ChangeBookOrderCommand(UUID bookId, Integer order) {
}
