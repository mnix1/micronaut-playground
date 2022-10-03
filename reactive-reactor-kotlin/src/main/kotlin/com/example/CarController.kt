package com.example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import kotlinx.coroutines.reactor.awaitSingle
import reactor.core.publisher.Flux
import reactor.util.function.Tuple2
import java.time.Duration
import java.util.stream.IntStream

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

    @Get("reactive-streaming-5")
    fun streaming5(): Flux<Long> {
        return Flux.interval(Duration.ofSeconds(1)).take(5)
    }

    @Get("reactive-streaming-boom")
    fun streamingBoom(): Flux<MutableList<Int>> {
        return Flux.fromStream(IntStream.range(0, 1000000).boxed()).map { IntStream.range(0, 1000000).boxed().toList() }
    }

    @Get("reactive-single")
    suspend fun single(): Long {
        return Flux.interval(Duration.ofSeconds(1)).take(3).last().awaitSingle();
    }

    @Get("blocking")
    fun listBlocking(): List<Car> {
        return cars
    }
}