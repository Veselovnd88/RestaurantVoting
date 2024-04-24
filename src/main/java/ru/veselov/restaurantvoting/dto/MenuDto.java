package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.veselov.restaurantvoting.util.HasId;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MenuDto(

        Integer id,

        LocalDate addedAt,

        List<DishDto> dishes,

        List<VoteDto> votes) implements HasId {

    @JsonCreator //constructor need int for Jackson mapping
    public MenuDto(@JsonProperty("id") Integer id, @JsonProperty("addedAt") LocalDate addedAt,
                   @JsonProperty("dishes") List<DishDto> dishes, @JsonProperty("votes") List<VoteDto> votes) {
        this.id = id;
        this.addedAt = addedAt;
        this.dishes = dishes;
        this.votes = votes;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
