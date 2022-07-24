package com.example;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class EventsTest {
    @Inject
    ApplicationEventPublisher<SimpleEvent> eventPublisher;
    @Inject
    SimpleEventListener simpleEventListener;
    @Inject
    OtherEventListener otherEventListener;

    @Test
    void eventsAreListened() {
        //when
        eventPublisher.publishEvent(new SimpleEvent(1));
        eventPublisher.publishEvent(new SimpleEvent(10));
        eventPublisher.publishEvent(new SimpleEvent(2));
        //then
        assertThat(simpleEventListener.sum).isEqualTo(13);
        assertThat(otherEventListener.last).isEqualTo(2);
    }


    record SimpleEvent(int value) {
    }

    @Singleton
    static class SimpleEventListener implements ApplicationEventListener<SimpleEvent> {
        private int sum = 0;

        @Override
        public void onApplicationEvent(SimpleEvent event) {
            sum += event.value;
        }
    }

    @Singleton
    static class OtherEventListener {
        private Integer last = null;

        @EventListener
        public void onSimpleEvent(SimpleEvent event) {
            last = event.value;
        }
    }
}
