package ru.veselov.restaurantvoting.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.veselov.restaurantvoting.util.HasId;
import ru.veselov.restaurantvoting.web.validaton.ValidationGroup;

public record UserDto(

        @JsonProperty("id")
        Integer id,

        @NotBlank
        @Size(min = 2, max = 100)
        @JsonProperty("name")
        String name,

        @Email
        @NotBlank
        @Size(max = 100)
        @JsonProperty("email")
        String email,

        @NotEmpty(groups = ValidationGroup.OnCreate.class)
        @Size(min = 5, max = 32, groups = ValidationGroup.OnCreate.class)
        @JsonProperty(value = "password")
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
