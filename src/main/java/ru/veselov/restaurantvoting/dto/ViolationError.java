package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ViolationError(

        String name,

        String message,

        String rejectedValue) {

    @JsonCreator //constructor need int for Jackson mapping
    public ViolationError(@JsonProperty("name") String name, @JsonProperty("message") String message,
                          @JsonProperty("rejectedValue") String rejectedValue) {
        this.name = name;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }
}