package ru.veselov.restaurantvoting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    MenuRepository menuRepository;

    @Mock
    RestaurantRepository repository;

    @InjectMocks
    MenuServiceImpl menuService;

    @BeforeEach
    void setUp(){

    }

}