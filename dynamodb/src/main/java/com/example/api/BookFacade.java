package com.example.api;

import com.example.api.command.*;

import java.util.UUID;

public interface BookFacade {
    UUID create(CreateBookCommand command);

    void comment(CommentBookCommand command);

    void makeAvailable(MakeBookAvailableCommand command);

    void makeUnavailable(MakeBookUnavailableCommand command);

    void changeOrder(ChangeBookOrderCommand command);
}
