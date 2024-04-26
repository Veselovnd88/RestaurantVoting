package ru.veselov.restaurantvoting.web.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.service.menu.MenuService;

import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL)
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Menu information", description = "Retrieving data about menus")
public class MenuController {

    public static final String REST_URL = "/api/v1/menus";

    private final MenuService service;

    @Operation(summary = "Get menus by restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menus of restaurant",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = MenuDto.class)))}
            )})
    @GetMapping("/restaurants/{restaurantId}")
    public List<MenuDto> getMenusByRestaurant(@PathVariable int restaurantId) {
        return service.getMenusByRestaurant(restaurantId);
    }

    @Operation(summary = "Get Menu by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find menu",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenuDto.class))})})
    @GetMapping("/{id}")
    public MenuDto getMenu(@PathVariable int id) {
        return service.getMenuByIdWithDishesAndVotes(id);
    }
}
