package com.example.book.component;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@MicronautTest(transactional = false)
@Testcontainers
class MongoRepositoryTest {

    static MongoDBContainer container;

    @BeforeAll
    static void beforeAll() {
        container = new MongoDBContainer("mongo:5.0.10").withEnv("MONGO_INITDB_DATABASE", "test");
        container.setPortBindings(List.of("27017:27017"));
        container.start();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Inject
    MongoBookRepository repository;

    @Test
    void deletesCommentFromManyRecords() {
        //given
        Book book1 = Book.create("Hobbit", 300, 300);
        book1.addComment("Nie polecam");
        book1.addComment("Super książka");
        Book book2 = Book.create("Matrix", 400, 400);
        book2.addComment("Nie polecam");
        Book book3 = Book.create("W pustyni i w puszczy", 500, 500);
        book3.addComment("Nie polecam!");
        book3.addComment("Beznadzieja");
        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        //when
        repository.deleteComments(List.of("Nie polecam", "Beznadzieja"));
        //then
        Assertions.assertThat(repository.get(book1.id).map(book -> book.comments)).contains(List.of("Super książka"));
        Assertions.assertThat(repository.get(book2.id).map(book -> book.comments)).contains(List.of());
        Assertions.assertThat(repository.get(book3.id).map(book -> book.comments)).contains(List.of("Nie polecam!"));
    }
}
