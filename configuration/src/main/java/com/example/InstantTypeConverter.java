package com.example;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.time.Instant;
import java.util.Optional;

@Singleton
class InstantTypeConverter  implements TypeConverter<String, Instant> {
    @Override
    public Optional<Instant> convert(String object, Class<Instant> targetType, ConversionContext context) {
        return Optional.of(Instant.parse(object));
    }
}
