package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.veselov.restaurantvoting.util.HasId;
import ru.veselov.restaurantvoting.web.validation.ValidationGroup;

public record UserDto(

        @JsonProperty("id")
        Integer id,

        @NotBlank
        @Size(min = 2, max = 100)
        @JsonProperty("name")
        @Schema(description = "User name", defaultValue = "Name")
        String name,

        @Email
        @NotBlank
        @Size(max = 100)
        @JsonProperty("email")
        @Schema(description = "User email", defaultValue = "email@email.com")
        String email,

        @NotEmpty(groups = ValidationGroup.OnCreate.class)
        @Size(min = 5, max = 32)
        @JsonProperty(value = "password")
        @Schema(description = "User password", defaultValue = "secretSecret")
        String password
) implements HasId {

    @Override
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public String password() {
        return password;
    }
}
