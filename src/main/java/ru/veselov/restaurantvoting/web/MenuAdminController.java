package ru.veselov.restaurantvoting.web;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.service.MenuService;

import java.time.LocalDate;

@RestController
@RequestMapping(value = MenuAdminController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Menu management", description = "Manage menus for admin")
public class MenuAdminController {

    public static final String REST_URL = "/api/v1/admin/menus";

    private final MenuService service;

    @PostMapping("/restaurants/{id}")
    public void add(@PathVariable int id, LocalDate localDate, NewMenuDto menuDto) {
        service.addMenu(id,localDate, menuDto);
    }

    @PutMapping("/{id}")
    void update(@PathVariable int id, NewMenuDto menuDto) {
        service.updateMenu(id, menuDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        service.deleteMenu(id);
    }
}