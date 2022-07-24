package com.example;

import io.micronaut.aop.Introduction;
import io.micronaut.context.annotation.Bean;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Introduction
@Bean
@Documented
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE, METHOD})
@interface Stub {
    String value() default "";
}
