package ru.veselov.restaurantvoting.service.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.InputMenuDto;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.exception.MenuNotFoundException;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private static final Sort SORT_BY_DATE = Sort.by(Sort.Direction.DESC, "date");

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper mapper;

    /**
     * Create new menu for restaurant for date
     *
     * @throws RestaurantNotFoundException                             if restaurant doesn't exist
     * @throws org.springframework.dao.DataIntegrityViolationException if menu/local date conflict occurred
     */
    @Caching(evict = {
            @CacheEvict(value = "restaurants", key = "#restaurantId"),
            @CacheEvict(value = "restaurants", key = "'restaraunts-with-menus'")
    })
    @Override
    @Transactional
    public MenuDto create(int restaurantId, LocalDate date) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        Menu menu = new Menu(date, restaurant);

        Menu saved = repository.save(menu);
        log.info("New menu for restaurant {} for date {} saved", restaurantId, date);
        return mapper.toDto(saved);
    }

    /**
     * Update menu
     *
     * @param id      menu id
     * @param menuDto menu data to update
     * @throws MenuNotFoundException                                   if menu with such id not found
     * @throws org.springframework.dao.DataIntegrityViolationException if dish exists for this menu
     * @throws org.springframework.dao.DataIntegrityViolationException if menu/local date conflict occurred
     */
    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    @Transactional
    public MenuDto update(int id, InputMenuDto menuDto) {
        Menu menu = findById(id);
        mapper.toEntityUpdate(menu, menuDto);
        Menu updated = repository.save(menu);
        log.info("Menu id: {} updated", id);
        return mapper.toDto(updated);
    }

    /**
     * Get menu by id, and map to dto
     *
     * @param id menu id
     * @return menu Dto with dishes but without votes
     * @throws MenuNotFoundException if menu for this id doesn't exist
     */
    @Override
    public MenuDto getMenuByIdWithDishes(int id) {
        log.info("Retrieving menu by id: {}", id);
        Menu menu = repository.findByIdWithDishes(id).orElseThrow(() -> new MenuNotFoundException(id));
        return mapper.toDto(menu);
    }

    /**
     * Get all menus of restaurant
     *
     * @param restaurantId restaurant id
     * @return list of menu dtos (with dishes)
     */
    @Override
    public List<MenuDto> getMenusByRestaurant(int restaurantId) {
        log.info("Retrieving all menus for restaurant id: {}", restaurantId);
        return mapper.toDtos(repository.findByRestaurantId(restaurantId, SORT_BY_DATE));
    }

    /**
     * Delete menu
     *
     * @param id menu id
     */
    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void deleteMenu(int id) {
        repository.deleteById(id);
        log.info("Menu with id: {} deleted", id);
    }

    public Menu findById(int id) {
        return repository.findById(id).orElseThrow(() -> new MenuNotFoundException(id));
    }
}
