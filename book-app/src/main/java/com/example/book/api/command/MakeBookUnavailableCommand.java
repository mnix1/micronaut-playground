package com.example.book.api.command;

import java.util.UUID;

public record MakeBookUnavailableCommand(UUID bookId) {}
