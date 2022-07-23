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
class SingletonRequiresPrototypeInterfaceTest {
    private final static Logger LOG = LoggerFactory.getLogger(SingletonRequiresPrototypeInterfaceTest.class);

    @Inject
    Optional<SimpleSingleton> simpleSingleton;

    @Inject
    Optional<SimpleBean> simpleBean;

    @Inject
    Optional<Simple> simple;

    @Test
    void doesNotExist() {
        simpleSingleton.ifPresent(SimpleSingleton::call);
        simpleBean.ifPresent(SimpleBean::call);
        simple.ifPresent(Simple::call);
        assertThat(simpleSingleton).isEmpty();
        assertThat(simpleBean).isEmpty();
        assertThat(simple).isEmpty();
    }

    @Test
    @Property(name = "bean", value = "true")
    void exists() {
        simpleSingleton.ifPresent(SimpleSingleton::call);
        simpleBean.ifPresent(SimpleBean::call);
        simple.ifPresent(Simple::call);
        assertThat(simpleSingleton).isEmpty();
        assertThat(simpleBean).isEmpty();
        assertThat(simple).isPresent();
    }

    interface Simple {
        void call();
    }

    static class SimpleSingleton implements Simple {
        public void call() {
            LOG.info("SimpleSingleton called");
        }
    }

    static class SimpleBean implements Simple {
        public void call() {
            LOG.info("SimpleBean called");
        }
    }

    @Bean
    @Requires(property = "bean", value = "true")
    Simple simpleBean() {
        return new SimpleBean();
    }

    @Singleton
    @Requires(bean = SimpleBean.class)
    SimpleSingleton simpleSingleton() {
        return new SimpleSingleton();
    }
}
