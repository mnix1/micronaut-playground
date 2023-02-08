package com.example;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.http.scope.RequestScope;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

//IOC-test-7
@MicronautTest
class RequestScopeTest {

    private static final Logger LOG = LoggerFactory.getLogger(RequestScopeTest.class);

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void initializesPerRequest() {
        for (int i = 1; i <= 10; i++) {
            expectResponse(i);
        }
    }

    private void expectResponse(int index) {
        HttpResponse<Integer> response = client.toBlocking().exchange(HttpRequest.GET("/"), Argument.of(Integer.class));
        assertThat(response.body()).isEqualTo(index);
    }

    @Controller("/")
    static class HelloController {

        private final SimpleRequestScope simpleRequestScope;

        HelloController(SimpleRequestScope simpleRequestScope) {
            this.simpleRequestScope = simpleRequestScope;
        }

        @Get
        int hello() {
            return simpleRequestScope.getCount();
        }
    }

    @RequestScope
    static class SimpleRequestScope {
        static int count = 0;

        public SimpleRequestScope() {
            LOG.info("SimpleRequestScope constructor initialized");
        }

        @PostConstruct
        void init() {
            count++;
            LOG.info("SimpleRequestScope initialized");
        }

        @PreDestroy
        void close() {
            LOG.info("SimpleRequestScope closed");
        }

        public int getCount() {
            return count;
        }
    }
}
