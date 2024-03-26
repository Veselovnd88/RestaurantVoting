package ru.veselov.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL)
@RequiredArgsConstructor
public class RestaurantController {

    public static final String REST_URL = "/api/v1/restaurants";

    private final RestaurantService service;

    @Operation(summary = "Получить все рестораны")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рестораны загружены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @GetMapping
    public List<RestaurantDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Получить ресторан по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан с id найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @GetMapping("/{id}")
    public RestaurantDto getOne(@PathVariable int id) {
        return service.findById(id);
    }

    @Operation(summary = "Получить ресторан по id, с меню и голосами на дату")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ресторан с id найден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @GetMapping("/{id}/with-menu")
    public RestaurantDto getWithMenuAndVotesByDate(@PathVariable int id,
                                                   @RequestParam(value = "date", required = false) LocalDate date) {
        return service.findByIdWithMenuAndVotesForDate(id, date == null ? LocalDate.now() : date);
    }
}
