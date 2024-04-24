package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.util.HasId;

public record InputRestaurantDto(

        @Nullable
        @JsonProperty("id")
        Integer id,

        @NotBlank
        @Size(min = 5, max = 125)
        @JsonProperty("name")
        String name
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
