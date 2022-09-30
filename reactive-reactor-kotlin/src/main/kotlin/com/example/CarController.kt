package com.example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import reactor.core.publisher.Flux
import reactor.util.function.Tuple2
import java.time.Duration

@Controller
internal class CarController(private val cars: List<Car>) {
    @Get("reactive-immediate")
    fun listReactiveImmediate(): Flux<Car> {
        return Flux.fromIterable(cars)
    }

    @Get("reactive-streaming")
    fun listReactiveStreaming(): Flux<Car> {
        return Flux.interval(Duration.ofSeconds(1)).zipWith(
            Flux.fromIterable(
                cars
            )
        ).map { obj: Tuple2<Long?, Car> -> obj.t2 }
    }

    @Get("reactive-streaming-endless")
    fun streamingEndless(): Flux<Long> {
        return Flux.interval(Duration.ofSeconds(1))
    }

    @Get("blocking")
    fun listBlocking(): List<Car> {
        return cars
    }
}