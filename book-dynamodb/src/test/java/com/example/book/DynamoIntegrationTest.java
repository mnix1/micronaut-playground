package com.example.book;

import com.example.book.view.BookView;
import com.example.book.view.DynamoBookViewRecord;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class DynamoIntegrationTest {
    @Inject
    BooksClient client;

    @Test
    void apiWorks() {
        //given
        UUID book1Id = client.create(new BookRequestBody("Władca Pierścieni", 1099));
        UUID book2Id = client.create(new BookRequestBody("Hobbit", 301));
        client.available(book1Id);
        client.comment(book2Id, new CommentRequestBody("Super książka"));
        client.comment(book2Id, new CommentRequestBody("Bardzo fajna"));
        //expect
        List<? extends BookView> records = client.list();
        assertThat(records).hasSize(2);
        List<? extends BookView> available = client.listAvailable();
        assertThat(available).hasSize(1);
        assertThat(available.get(0).id()).isEqualTo(book1Id);
        assertThat(available.get(0).comments()).isNull();
        assertThat(available.get(0).name()).isEqualTo("Władca Pierścieni");
        assertThat(available.get(0).pages()).isEqualTo(1099);
        List<? extends BookView> unavailable = client.listUnavailable();
        assertThat(unavailable).hasSize(1);
        assertThat(unavailable.get(0).id()).isEqualTo(book2Id);
        assertThat(unavailable.get(0).comments()).isEqualTo(List.of("Super książka", "Bardzo fajna"));
        assertThat(unavailable.get(0).name()).isEqualTo("Hobbit");
        assertThat(unavailable.get(0).pages()).isEqualTo(301);
        //and
        client.unavailable(book1Id);
        available = client.listAvailable();
        assertThat(available).isEmpty();
        unavailable = client.listUnavailable();
        assertThat(unavailable).hasSize(2);
    }

    @Client("/books")
    interface BooksClient {
        @Post
        UUID create(BookRequestBody requestBody);

        @Post("/{id}/comment")
        void comment(UUID id, CommentRequestBody requestBody);

        @Get
        List<DynamoBookViewRecord> list();

        @Get("?available=true")
        List<DynamoBookViewRecord> listAvailable();

        @Get("?available=false")
        List<DynamoBookViewRecord> listUnavailable();

        @Put("/{id}/available")
        void available(UUID id);

        @Put("/{id}/unavailable")
        void unavailable(UUID id);
    }

    record BookRequestBody(String name, Integer pages) {
    }

    record CommentRequestBody(String comment) {
    }
}
