package ru.veselov.restaurantvoting.service.dish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.exception.DishNotFoundException;
import ru.veselov.restaurantvoting.exception.MenuNotFoundException;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.repository.DishRepository;
import ru.veselov.restaurantvoting.repository.MenuRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DishServiceImpl implements DishService {

    private final DishRepository repository;
    private final MenuRepository menuRepository;
    private final DishMapper mapper;

    /**
     * Save new dish
     *
     * @param menuId  id of chosen menu
     * @param dishDto dto for creating new Dish
     * @return {@link DishDto} saved dish
     */
    @CacheEvict(value = {"restaurants", "menus"}, allEntries = true)
    @Override
    @Transactional
    public DishDto save(int menuId, DishDto dishDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException(menuId));
        Dish dish = mapper.toEntity(dishDto);
        dish.setMenu(menu);
        Dish savedDish = repository.save(dish);
        log.info("New dish saved with id: {}", savedDish.getId());
        return mapper.toDto(savedDish);
    }

    /**
     * Update dish
     *
     * @param id      dish id for updating
     * @param dishDto dto with data to update
     * @return {@link DishDto}
     */
    @CacheEvict(value = {"restaurants", "menus"}, allEntries = true)
    @Override
    @Transactional
    public DishDto update(int id, DishDto dishDto) {
        Dish foundDish = repository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
        foundDish.setName(dishDto.name());
        foundDish.setPrice(dishDto.price());
        Dish updatedDish = repository.save(foundDish);
        log.info("Dish with id: {}} successfully updated", id);
        return mapper.toDto(updatedDish);
    }

    /**
     * Delete dish
     *
     * @param id of dish to delete
     */
    @CacheEvict(value = {"restaurants", "menus"}, allEntries = true)
    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
        log.info("Dish with id: {} deleted", id);
    }

    /**
     * Find dish by id
     *
     * @param id of dish
     * @return {@link DishDto} found dish
     */
    @Override
    public DishDto findOne(int id) {
        Dish dish = repository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
        log.debug("Retrieving dish with id: {}", id);
        return mapper.toDto(dish);
    }
}
