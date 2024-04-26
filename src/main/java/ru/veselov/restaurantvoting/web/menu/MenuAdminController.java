package ru.veselov.restaurantvoting.web.menu;


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
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.service.menu.MenuService;
import ru.veselov.restaurantvoting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(value = MenuAdminController.REST_URL)
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Menu management", description = "Manage menus for admin")
public class MenuAdminController {

    public static final String REST_URL = "/api/v1/admin/menus";

    private final MenuService service;

    @Operation(summary = "Add menu to restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu added",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenuDto.class))})})
    @PostMapping("/restaurants/{restaurantId}")
    public ResponseEntity<MenuDto> add(@PathVariable int restaurantId, @Valid @RequestBody InputMenuDto menuDto) {
        ValidationUtil.checkNew(menuDto);
        MenuDto created = service.create(restaurantId, menuDto);
        URI uriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuController.REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfResource).body(created);
    }

    @Operation(summary = "Update menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu updated",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MenuDto.class))})})

    @PutMapping("/{id}")
    public MenuDto update(@PathVariable int id, @Valid @RequestBody InputMenuDto menuDto) {
        ValidationUtil.assureIdConsistent(menuDto, id);
        return service.update(id, menuDto);
    }

    @Operation(summary = "Delete menu and it's dishes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deleted")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id) {
        service.deleteMenu(id);
    }
}
