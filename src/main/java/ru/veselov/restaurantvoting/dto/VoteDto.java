package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VoteDto(
        @JsonProperty("restaurantId")
        Integer restaurantId,
        @JsonProperty("votedAt")
        LocalDate votedAt) {
}
