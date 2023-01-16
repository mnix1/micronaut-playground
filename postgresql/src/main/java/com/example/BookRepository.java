package com.example;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository
interface BookRepository extends PageableRepository<BookRecord, String> {
}
