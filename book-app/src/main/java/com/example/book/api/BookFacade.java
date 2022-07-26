package com.example.book.api;

import com.example.book.api.command.*;

import java.util.UUID;

public interface BookFacade {
    UUID create(CreateBookCommand command);

    void comment(CommentBookCommand command);

    void makeAvailable(MakeBookAvailableCommand command);

    void makeUnavailable(MakeBookUnavailableCommand command);

    void increaseOrder(BookOrderIncreaseCommand command);

    void decreaseOrder(BookOrderDecreaseCommand command);
}
