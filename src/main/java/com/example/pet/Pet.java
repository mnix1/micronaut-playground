package com.example.pet;

import java.time.Instant;

public record Pet(
        String name,
        PetType type,
        Instant birthTimestamp
) {
}
