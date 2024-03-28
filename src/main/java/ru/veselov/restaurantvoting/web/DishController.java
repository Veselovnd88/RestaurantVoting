package ru.veselov.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = DishController.REST_URL)
@RequiredArgsConstructor
public class DishController {

    public static final String REST_URL = "/api/v1/dishes";
}
