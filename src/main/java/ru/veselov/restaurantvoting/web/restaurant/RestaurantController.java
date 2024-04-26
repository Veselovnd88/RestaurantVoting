package ru.veselov.restaurantvoting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.service.restaurant.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL)
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Restaurant information", description = "Retrieving data about restaurants")
public class RestaurantController {

    public static final String REST_URL = "/api/v1/restaurants";

    private final RestaurantService service;

    @Operation(summary = "Get all restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @GetMapping
    public List<RestaurantDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get restaurant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @GetMapping("/{id}")
    public RestaurantDto getOne(@PathVariable int id) {
        return service.findById(id);
    }

    @Operation(summary = "Get restaurant by id with menu and votes for date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @GetMapping("/{id}/with-menu")
    public RestaurantDto getWithMenuAndVotesByDate(@PathVariable int id,
                                                   @RequestParam(value = "date", required = false) LocalDate date) {
        return service.findByIdWithMenuAndVotesForDate(id, date == null ? LocalDate.now() : date);
    }
}
