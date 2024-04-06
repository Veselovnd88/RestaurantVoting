package ru.veselov.restaurantvoting.web;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.service.MenuService;

import java.net.URI;

@RestController
@RequestMapping(value = MenuAdminController.REST_URL)
@RequiredArgsConstructor
@Validated
@Tag(name = "Menu management", description = "Manage menus for admin")
public class MenuAdminController {

    public static final String REST_URL = "/api/v1/admin/menus";

    private final MenuService service;

    @PostMapping("/restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MenuDto> add(@PathVariable int restaurantId, @Valid @RequestBody NewMenuDto menuDto) {
        MenuDto created = service.create(restaurantId, menuDto);
        URI uriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuAdminController.REST_URL + "/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uriOfResource).body(created);
    }

    @PutMapping("/{id}")
    void update(@PathVariable int id, NewMenuDto menuDto) {
        service.update(id, menuDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        service.deleteMenu(id);
    }
}
