package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.veselov.restaurantvoting.util.HasId;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MenuDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("date")
        LocalDate date,

        @JsonProperty("dishes")
        List<DishDto> dishes,

        @JsonProperty("votes")
        List<VoteDto> votes
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
