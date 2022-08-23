package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class FilterTest {

    @Inject
    CompaniesClient client;

    @BeforeEach
    void setUp() {
        client.delete();
    }

    @Test
    //TODO sometimes fails, why?
    void countsRequests() {
        for (int i = 0; i < 10; i++) {
            client.createRandom();
        }
        assertThat(SimpleFilter.counter).isEqualTo(10);
    }

    @Filter("/companies/random")
    static class SimpleFilter implements HttpServerFilter {

        static int counter = 0;

        @Override
        public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
            counter++;
            return chain.proceed(request);
        }
    }
}
