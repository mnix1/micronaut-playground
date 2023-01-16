package com.example;


import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@MappedEntity(value = "book")
public class BookRecord {

    @Id
    private String id;

    private String name;
    private Integer order;
    private boolean available;
    private Integer pages;
    private Instant createdTimestamp;
    private Instant updatedTimestamp;
    private String comments;
}
