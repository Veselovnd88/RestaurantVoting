package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViolationError(

        @JsonProperty("name")
        String name,

        @JsonProperty("message")
        String message,

        @JsonProperty("rejectedValue")
        String rejectedValue) {
}