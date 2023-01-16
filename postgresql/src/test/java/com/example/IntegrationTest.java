package com.example;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


@MicronautTest
class IntegrationTest {

    @Inject
    BookRepository repository;

    @Test
    void test() {
        Page<BookRecord> all = repository.findAll(Pageable.UNPAGED);
        assertThat(all).isEmpty();
        BookRecord record = new BookRecord("1", "Hobbit", 1, true, 23, Instant.now().truncatedTo(ChronoUnit.MILLIS), Instant.now().truncatedTo(ChronoUnit.MILLIS), "Nice book");
        repository.save(record);
        all = repository.findAll(Pageable.from(0));
        assertThat(all.getTotalSize()).isEqualTo(1);
        assertThat(all.getTotalPages()).isEqualTo(1);
        assertThat(all.getContent().get(0)).isEqualTo(record);
        assertThat(repository.findById("1")).contains(record);
    }
}
