package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.util.HasId;

public record DishDto(

        @Schema(description = "Dish id", defaultValue = "1000000")
        @Nullable
        @JsonProperty("id")
        Integer id,

        @Schema(description = "Dish name", defaultValue = "salmon sushi")
        @NotBlank
        @Size(min = 2, max = 255)
        @JsonProperty("name")
        String name,

        @Schema(description = "Dish price", defaultValue = "100000")
        @Range(min = 1000, max = 1000000)
        @JsonProperty("price")
        int price
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
