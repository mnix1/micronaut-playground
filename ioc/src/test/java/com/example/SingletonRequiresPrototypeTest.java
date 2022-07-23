package com.example;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(rebuildContext = true)
@ExtendWith(AlwaysRebuildContextJunit5Extension.class)
class SingletonRequiresPrototypeTest {
    private final static Logger LOG = LoggerFactory.getLogger(SingletonRequiresPrototypeTest.class);

    @Inject
    Optional<SimpleSingleton> simpleSingleton;

    @Inject
    Optional<SimpleBean> simpleBean;

    @Test
    void doesNotExist() {
        simpleSingleton.ifPresent(SimpleSingleton::call);
        simpleBean.ifPresent(SimpleBean::call);
        assertThat(simpleSingleton).isEmpty();
        assertThat(simpleBean).isEmpty();
    }

    @Test
    @Property(name = "bean", value = "true")
    void exists() {
        simpleSingleton.ifPresent(SimpleSingleton::call);
        simpleBean.ifPresent(SimpleBean::call);
        assertThat(simpleSingleton).isPresent();
        assertThat(simpleBean).isPresent();
    }

    @Singleton
    @Requires(bean = SimpleBean.class)
    static class SimpleSingleton {
        void call() {
            LOG.info("SimpleSingleton called");
        }
    }

    @Bean
    @Requires(property = "bean", value = "true")
    static class SimpleBean {
        void call() {
            LOG.info("SimpleBean called");
        }
    }
}
