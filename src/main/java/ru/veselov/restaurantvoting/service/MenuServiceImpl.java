package ru.veselov.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
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
@Transactional(readOnly = true)
@Slf4j
public class MenuServiceImpl implements MenuService {

    private static final Sort SORT_BY_DATE = Sort.by(Sort.Direction.DESC, "addedAt");

    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper mapper;

    /**
     * Create new menu for restaurant for date
     *
     * @throws MenuNotFoundException                                   if restaurant doesn't exist
     * @throws org.springframework.dao.DataIntegrityViolationException if menu/local date conflict occurred
     */
    @Override
    @Transactional
    public MenuDto create(int restaurantId, InputMenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        Menu menu = mapper.toEntity(menuDto);
        menu.setRestaurant(restaurant);

        Menu saved = repository.save(menu);
        log.info("New menu for restaurant {} for date {} saved", restaurantId, menuDto.addedAt());
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
    @Override
    @Transactional
    public MenuDto update(int id, InputMenuDto menuDto) {
        Menu menu = repository.findById(id).orElseThrow(() -> new MenuNotFoundException(id));
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
    public MenuDto getMenuByIdWithDishesAndVotes(int id) {
        log.info("Retrieving menu by id: {}", id);
        Menu menu = repository.findByIdWithDishesAndVotes(id).orElseThrow(() -> new MenuNotFoundException(id));
        return mapper.toDto(menu);
    }

    /**
     * Get all menus of restaurant
     *
     * @param restaurantId restaurant id
     * @return list of menu dtos (with dishes, without votes)
     */
    @Override
    public List<MenuDto> getMenusByRestaurant(int restaurantId) {
        log.info("Retrieving all menus for restaurant id: {}", restaurantId);
        return mapper.toDtosWithoutVotes(repository.findByRestaurantId(restaurantId, SORT_BY_DATE));
    }

    /**
     * Delete menu
     *
     * @param id menu id
     */
    @Override
    @Transactional
    public void deleteMenu(int id) {
        repository.deleteById(id);
        log.info("Menu with id: {} deleted", id);
    }

    /**
     * Find menu by id without mapping to dto
     *
     * @param id menu id
     * @throws MenuNotFoundException if menu doesn't exist
     */
    @Override
    @NonNull
    public Menu findMenuById(int id) {
        return repository.findById(id).orElseThrow(() -> new MenuNotFoundException(id));
    }

    /**
     * Find menu by restaurant id for specified date
     *
     * @param restaurantId id of restaurant
     * @param localDate    date
     * @return Menu object
     * @throws MenuNotFoundException if no menu found for such conditions
     */
    @Override
    public Menu findMenuByRestaurantIdAndLocalDate(int restaurantId, LocalDate localDate) {
        return repository.findByRestaurantIdByDate(restaurantId, localDate)
                .orElseThrow(() -> new MenuNotFoundException(restaurantId, localDate));
    }
}
