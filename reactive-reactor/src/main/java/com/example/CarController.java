package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;

@Controller
class CarController {
    private final List<Car> cars;

    CarController(List<Car> cars) {
        this.cars = cars;
    }

    @Get("reactive-immediate")
    Flux<Car> listReactiveImmediate() {
        return Flux.fromIterable(cars);
    }

    @Get("reactive-streaming")
    Flux<Car> listReactiveStreaming() {
        return Flux.interval(Duration.ofSeconds(1)).zipWith(Flux.fromIterable(cars)).map(Tuple2::getT2);
    }

    @Get("reactive-streaming-endless")
    Flux<Long> streamingEndless() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    @Get("blocking")
    List<Car> listBlocking() {
        return cars;
    }
}
