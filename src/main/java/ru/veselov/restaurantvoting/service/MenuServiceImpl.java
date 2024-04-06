package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.MenuDto;
import ru.veselov.restaurantvoting.dto.NewMenuDto;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

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
     */
    @Override
    @Transactional
    public MenuDto create(int restaurantId, NewMenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
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
     */
    @Override
    @Transactional
    public void update(int id, NewMenuDto menuDto) {
        Menu menu = getById(id);
        mapper.toEntityUpdate(menu, menuDto);
        repository.save(menu);
        log.info("Menu id: {} updated", id);
    }

    /**
     * Get menu by id
     *
     * @param id menu id
     * @return menu Dto with dishes but without votes
     */
    @Override
    public MenuDto getMenuById(int id) {
        log.info("Retrieving menu by id: {}", id);
        return mapper.toDto(getById(id));
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
        return mapper.toDtos(repository.findByRestaurantId(restaurantId, SORT_BY_DATE));
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
     * Get menu by id or throw exception
     *
     * @param id menu id
     */
    private Menu getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No menu found with id"));
    }
}
