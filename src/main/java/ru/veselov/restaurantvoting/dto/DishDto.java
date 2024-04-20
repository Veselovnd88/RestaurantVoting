package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //FIXME can this work w/o this?
public record DishDto(

        @Schema(description = "Dish id", defaultValue = "1000000")
        @Nullable
        Integer id,

        @Schema(description = "Dish name", defaultValue = "salmon sushi")
        @NotBlank
        @Size(min = 2, max = 255)
        String name,

        @Schema(description = "Dish price", defaultValue = "100000")
        @Range(min = 1000, max = 1000000)
        int price) {

    @JsonCreator //constructor need int for Jackson mapping
    public DishDto(@JsonProperty("id") Integer id, @JsonProperty("name") String name,
                   @JsonProperty("price") int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
