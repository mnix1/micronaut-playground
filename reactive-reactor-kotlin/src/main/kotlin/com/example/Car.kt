package com.example

import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.EachProperty
import io.micronaut.core.annotation.Introspected
import java.time.Instant

@EachProperty(value = "cars", list = true)
@Introspected
data class Car
@ConfigurationInject
constructor(
    val name: String, val mass: Int, val type: CarType, val produced: Instant
)