package com.example.book.component.http;

import com.example.book.api.BookFacade;
import com.example.book.api.command.*;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import java.util.UUID;

@Controller("/books")
class BookController {

    private final BookFacade facade;

    BookController(BookFacade facade) {
        this.facade = facade;
    }

    @Post
    UUID create(BookRequestBody requestBody) {
        return facade.create(requestBody.toCommand());
    }

    @Post("/{id}/comment")
    void comment(UUID id, CommentRequestBody requestBody) {
        facade.comment(new CommentBookCommand(id, requestBody.comment));
    }

    @Put("/{id}/available")
    void available(UUID id) {
        facade.makeAvailable(new MakeBookAvailableCommand(id));
    }

    @Put("/{id}/unavailable")
    void unavailable(UUID id) {
        facade.makeUnavailable(new MakeBookUnavailableCommand(id));
    }

    @Put("/{id}/increase-order")
    void increaseOrder(UUID id) {
        facade.increaseOrder(new BookOrderIncreaseCommand(id));
    }

    @Put("/{id}/decrease-order")
    void decreaseOrder(UUID id) {
        facade.decreaseOrder(new BookOrderDecreaseCommand(id));
    }

    record BookRequestBody(String name, Integer pages) {
        CreateBookCommand toCommand() {
            return new CreateBookCommand(name, pages);
        }
    }

    record CommentRequestBody(String comment) {}
}
