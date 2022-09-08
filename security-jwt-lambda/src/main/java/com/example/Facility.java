package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

@JsonSerialize
@JsonDeserialize
class Facility {

    private final String country;
    private final String city;

    @JsonCreator
    Facility(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String country() {
        return country;
    }

    public String city() {
        return city;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Facility) obj;
        return Objects.equals(this.country, that.country) && Objects.equals(this.city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city);
    }

    @Override
    public String toString() {
        return "Facility[" + "country=" + country + ", " + "city=" + city + ']';
    }
}
