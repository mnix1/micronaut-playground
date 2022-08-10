package com.example.component.http;

import com.example.api.BookFacade;
import com.example.api.command.CommentBookCommand;
import com.example.api.command.CreateBookCommand;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.UUID;


@Controller("/books")
class CreateBookController {
    private final BookFacade facade;

    CreateBookController(BookFacade facade) {
        this.facade = facade;
    }

    @Post
    UUID create(BookRequestBody requestBody) {
        return facade.create(requestBody.toCommand());
    }

    @Post("/{id}")
    void comment(UUID id, CommentRequestBody requestBody) {
        facade.comment(new CommentBookCommand(id, requestBody.comment));
    }

    private record BookRequestBody(String name, Integer pages) {
        CreateBookCommand toCommand() {
            return new CreateBookCommand(name, pages);
        }
    }

    private record CommentRequestBody(String comment) {
    }
}
