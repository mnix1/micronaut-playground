package com.example.api.command;

import java.util.UUID;

public record MakeBookUnavailableCommand(UUID bookId) {
}
