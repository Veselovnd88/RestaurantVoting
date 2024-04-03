package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuServiceImpl implements MenuService {

    private static final Sort SORT_BY_DATE = Sort.by(Sort.Direction.DESC, "addedAt");

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper mapper;
    private final DishMapper dishMapper;

    /**
     * Create new menu for restaurant for date
     */
    @Override
    @Transactional
    public void add(int restaurantId, LocalDate localDate, NewMenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        Menu menu = new Menu(null, localDate, restaurant,
                menuDto.dishes().stream().map(dishMapper::toEntity).collect(Collectors.toSet()));
        repository.save(menu);
        log.info("New menu for restaurant {} for date {} saved", restaurantId, localDate);
    }

    @Override
    @Transactional
    public void updateMenu(int id, NewMenuDto menuDto) {
        Menu menu = getById(id);
        menu.setDishes(menuDto.dishes().stream().map(dishMapper::toEntity).collect(Collectors.toSet()));
        repository.save(menu);
        log.info("Menu id: {} updated", id);
    }

    @Override
    public MenuDto getMenuById(int id) {
        log.info("Retrieving menu by id: {}", id);
        return mapper.toDto(getById(id));
    }

    @Override
    public List<MenuDto> getMenusByRestaurant(int restaurantId) {
        log.info("Retrieving all menus for restaurant id: {}", restaurantId);
        return mapper.toDtos(repository.findByRestaurantId(restaurantId, SORT_BY_DATE));
    }

    @Override
    @Transactional
    public void deleteMenu(int id) {
        repository.deleteById(id);
        log.info("Menu with id: {} deleted", id);
    }

    private Menu getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No menu found with id"));
    }
}
