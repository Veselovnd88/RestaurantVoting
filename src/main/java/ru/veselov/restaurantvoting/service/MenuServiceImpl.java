package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.DishRepository;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper mapper;

    @Override
    @Transactional
    public void addMenu(int restaurantId, LocalDate localDate, NewMenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        Set<Dish> dishes = menuDto.dishes().stream().map(d -> {
            assert d.id() != null; //#TODO replace for validationUtil
            return dishRepository.findById(d.id())
                    .orElse(new Dish(null, d.name(), d.price()));
        }).collect(Collectors.toSet());
        Menu menu = new Menu(null, localDate, restaurant, dishes);
        repository.save(menu);
        log.info("New menu for restaurant {} for date {} saved", restaurantId, localDate);
    }

    @Override
    public void updateMenu(int id, NewMenuDto menuDto) {

    }

    @Override
    public MenuDto getMenuById(int id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No menu found with id")));
    }

    @Override
    public List<MenuDto> getMenusByRestaurant(int restaurantId) {
        return null;
    }

    @Override
    public void deleteMenu(int id) {
        repository.deleteById(id);
        log.info("Menu with id: {} deleted", id);
    }
}
