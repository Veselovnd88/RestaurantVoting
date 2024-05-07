package ru.veselov.restaurantvoting.web.dish;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.service.dish.DishService;
import ru.veselov.restaurantvoting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(value = DishAdminController.REST_URL)
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Dish management", description = "Manage dishes for admin")
public class DishAdminController {

    public static final String REST_URL = "/api/v1/admin/dishes";

    private final DishService service;

    @Operation(summary = "Add new dish to menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DishDto.class))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DishDto> create(@RequestParam("menuId") int menuId, @Valid @RequestBody DishDto dishDto) {
        ValidationUtil.checkNew(dishDto);
        DishDto created = service.save(menuId, dishDto);
        URI uriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DishController.REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfResource).body(created);
    }

    @Operation(summary = "Update dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish updated",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DishDto.class))})})
    @PutMapping("/{id}")
    public DishDto update(@PathVariable int id, @Valid @RequestBody DishDto dishDto) {
        ValidationUtil.assureIdConsistent(dishDto, id);
        return service.update(id, dishDto);
    }

    @Operation(summary = "Delete dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dish delete")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
