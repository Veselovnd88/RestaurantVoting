package ru.veselov.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.NewRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/admin/restaurants")
@RequiredArgsConstructor
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Добавить ресторан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ресторан создан",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto create(@RequestBody NewRestaurantDto restaurantDto) {
        return restaurantService.create(restaurantDto);
    }

    @Operation(summary = "Обновить данные ресторана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Обновлено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})
    })
    @PutMapping("/{id}")
    public RestaurantDto update(@PathVariable int id, @RequestBody NewRestaurantDto restaurantDto) {
        return restaurantService.update(id, restaurantDto);
    }

    @Operation(summary = "Удалить ресторан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Обновлено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }
}
