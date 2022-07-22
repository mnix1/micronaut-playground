package com.example;

import io.micronaut.runtime.Micronaut;

class HTTPApplication {
    public static void main(String[] args) {
        Micronaut.run(HTTPApplication.class, args);
    }
}
