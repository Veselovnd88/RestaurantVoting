package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.repository.DishRepository;
import ru.veselov.restaurantvoting.repository.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DishServiceImpl implements DishService {

    public static final String NOT_FOUND_MSG = "Dish with id: %s not found";

    private static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");
    private final DishRepository repository;
    private final MenuRepository menuRepository;
    private final DishMapper mapper;

    /**
     * Save new dish
     * @param menuId id of chosen menu
     * @param dishDto dto for creating new Dish
     * @return {@link DishDto} saved dish
     */
    @Override
    @Transactional
    public DishDto save(int menuId, DishDto dishDto) {
        Menu menu = menuRepository.findByIdWithDishesAndVotes(menuId).orElseThrow(() -> new EntityNotFoundException("Menu with id not found"));
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
    @Override
    @Transactional
    public DishDto update(int id, DishDto dishDto) {
        Dish foundDish = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MSG.formatted(id)));
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
        Dish dish = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MSG.formatted(id)));
        log.debug("Retrieving dish with id: {}", id);
        return mapper.toDto(dish);
    }

    /**
     * Get all dishes
     *
     * @return {@link DishDto} list of dish dtos
     */
    @Override
    public List<DishDto> findAll() {
        log.debug("Retrieving all dishes from db");
        return mapper.toDtos(repository.findAll(SORT_BY_NAME));
    }

    /**
     * Get all by restaurant id
     *
     * @param id of restaurant
     * @return {@link DishDto} list of dish dtos
     */
    @Override
    public List<DishDto> findAllByRestaurantId(int id) {
        return mapper.toDtos(repository.findAllByRestaurantId(id, SORT_BY_NAME));
    }
}
