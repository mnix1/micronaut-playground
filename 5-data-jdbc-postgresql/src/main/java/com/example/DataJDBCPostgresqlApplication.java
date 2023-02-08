package com.example;

import io.micronaut.runtime.Micronaut;

class DataJDBCPostgresqlApplication {

    public static void main(String[] args) {
        System.setProperty("micronaut.environments", "dev");
        Micronaut.run(DataJDBCPostgresqlApplication.class, args);
    }
}
