package com.example;

import io.micronaut.context.BeanContext;
import io.micronaut.http.annotation.Controller;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

//IOC-test-10
@MicronautTest
class BeanAnnotationMetadataTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeanAnnotationMetadataTest.class);

    @Inject
    BeanContext beanContext;

    @Test
    void findsAllControllers() {
        Collection<BeanDefinition<?>> definitions = beanContext.getBeanDefinitions(Qualifiers.byStereotype(Controller.class));
        for (BeanDefinition<?> definition : definitions) {
            LOG.info(definition.toString());
        }
        assertThat(definitions.stream().map(BeanDefinition::getName)).contains("com.example.VehicleController");
    }
}
