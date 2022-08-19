package com.example;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.beans.BeanWrapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BeanIntrospectionTest {

    @Test
    void introspectsPerson() {
        BeanIntrospection<Person> introspection = BeanIntrospection.getIntrospection(Person.class);
        Person person = introspection.instantiate("John");
        assertThat(person.name).isEqualTo("John");

        Optional<BeanProperty<Person, String>> optionalProperty = introspection.getProperty("name", String.class);
        assertThat(optionalProperty.map(p -> p.get(person))).contains("John");

        BeanProperty<Person, String> property = introspection.getRequiredProperty("name", String.class);
        assertThat(property.get(person)).isEqualTo("John");

        assertThat(introspection.getAnnotationNames()).containsExactly("io.micronaut.core.annotation.Introspected");
        assertThat(introspection.getPropertyNames()).containsExactly("name", "age");
    }

    @Test
    void wrapsPerson() {
        BeanWrapper<Person> wrapper = BeanWrapper.getWrapper(new Person("Fred"));
        wrapper.setProperty("age", "20");
        int newAge = wrapper.getRequiredProperty("age", int.class);
        assertThat(newAge).isEqualTo(20);
    }

    @Introspected
    static class Person {

        private String name;
        private int age = 18;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
