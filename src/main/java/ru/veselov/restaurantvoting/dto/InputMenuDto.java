package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import ru.veselov.restaurantvoting.util.HasId;

import java.time.LocalDate;

public record InputMenuDto(

        @Nullable
        @JsonProperty("id")
        Integer id,

        @NotNull
        @JsonProperty("date")
        @Schema(description = "Day of menu added", defaultValue = "2024-04-26")
        LocalDate date
) implements HasId {

    @Override
    public Integer getId() {
        return this.id;
    }
}
