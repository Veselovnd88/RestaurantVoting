package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.model.Dish;
import ru.veselov.restaurantvoting.repository.DishRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DishServiceImpl implements DishService {

    public static final String NOT_FOUND_MSG = "Dish with id: %s not found";
    private final DishRepository repository;
    private final DishMapper mapper;

    /**
     * Save new dish
     *
     * @param dishDto dto for creating new Dish
     * @return {@link DishDto} saved dish
     */
    @Override
    @Transactional
    public DishDto save(DishDto dishDto) {
        Dish savedDish = repository.save(mapper.toEntity(dishDto));
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
        return mapper.toDtos(repository.findAll());
    }
}
