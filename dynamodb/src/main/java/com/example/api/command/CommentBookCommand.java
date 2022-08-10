package com.example.api.command;

import java.util.UUID;

public record CommentBookCommand(UUID bookId, String comment) {
}
