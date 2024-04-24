package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record VoteDto(
        @JsonProperty("restaurantId")
        Integer restaurantId,
        @JsonProperty("votedAt")
        LocalDate votedAt,
        @JsonProperty("user")
        UserDto user) {
}
