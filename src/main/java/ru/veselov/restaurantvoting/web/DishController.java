package ru.veselov.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.service.DishService;

import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Dish information", description = "Retrieving data about dishes")
public class DishController {

    public static final String REST_URL = "/api/v1/dishes";

    private final DishService service;

    @Operation(summary = "Get dish by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish retrieved",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DishDto.class))})})
    @GetMapping("/{id}")
    public DishDto getOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @Operation(summary = "Get all dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dishes",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DishDto.class)))}
            )})
    @GetMapping
    public List<DishDto> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Get all dishes for restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dishes of restaurant",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DishDto.class)))}
            )})
    @GetMapping("/restaurants/{id}")
    public List<DishDto> getAllByRestaurant(@PathVariable int id) {
        return service.findAllByRestaurantId(id);
    }
}
