package com.example.audit;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.time.Instant;

@MappedEntity(value = "audit")
public class AuditEntity {

    @Id
    String id;

    Instant timestamp;
    String body;

    public AuditEntity(String id, Instant timestamp, String body) {
        this.id = id;
        this.timestamp = timestamp;
        this.body = body;
    }

    public AuditEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
