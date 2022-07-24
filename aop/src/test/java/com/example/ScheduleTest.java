package com.example;

import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class ScheduleTest {
    private final static Logger LOG = LoggerFactory.getLogger(ScheduleTest.class);
    @Inject
    ScheduleExample example;

    @Test
    void adds() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            assertThat(example.timestamps).hasSize(i);
            LOG.info(example.timestamps.toString());
            Thread.sleep(1000);
        }
    }

    @Singleton
    static class ScheduleExample {
        List<Instant> timestamps = new ArrayList<>();

        @Scheduled(fixedRate = "1s")
        void add() {
            Instant now = Instant.now();
            LOG.info("ADD {} ", now);
            timestamps.add(now);
        }
    }
}
