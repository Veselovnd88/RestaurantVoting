package ru.veselov.restaurantvoting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.service.restaurant.RestaurantService;
import ru.veselov.restaurantvoting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(value = RestaurantAdminController.REST_URL)
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Restaurant management", description = "Manage restaurants for admin")
public class RestaurantAdminController {

    public static final String REST_URL = "/api/v1/admin/restaurants";

    private final RestaurantService service;

    @Operation(summary = "Add restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody InputRestaurantDto restaurantDto) {
        ValidationUtil.checkNew(restaurantDto);
        RestaurantDto created = service.create(restaurantDto);
        URI uriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantController.REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfResource).body(created);
    }

    @Operation(summary = "Update restaurant data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RestaurantDto.class))})
    })
    @PutMapping("/{id}")
    public RestaurantDto update(@PathVariable int id, @Valid @RequestBody InputRestaurantDto restaurantDto) {
        ValidationUtil.assureIdConsistent(restaurantDto, id);
        return service.update(id, restaurantDto);
    }

    @Operation(summary = "Remove restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant removed")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
