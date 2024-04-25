package ru.veselov.restaurantvoting.service.restaurant;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.model.Menu;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.MenuRepository;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    public static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;
    private final MenuRepository menuRepository;

    /**
     * Create restaurant
     *
     * @param restaurantDto dto with initial data about restaurant
     * @return {@link RestaurantDto} dto with data about saved restaurant
     */
    @CacheEvict(value = {"restaurants", "menus"}, allEntries = true)
    @Override
    @Transactional
    public RestaurantDto create(InputRestaurantDto restaurantDto) {
        Restaurant savedRestaurant = repository.save(mapper.toEntity(restaurantDto));
        log.info("New restaurant: {} successfully saved", savedRestaurant.getName());
        return mapper.entityToDtoWithMenus(savedRestaurant);
    }

    /**
     * Get all restaurants with names and ids
     *
     * @return {@link RestaurantDto} list of dtos
     */
    @Cacheable(value = "restaurants")
    @Override
    public List<RestaurantDto> getAll() {
        log.info("Retrieving restaurants with votes from repository");
        return mapper.entitiesToDto(repository.findAll(SORT_BY_NAME));
    }

    /**
     * Get restaurant by id
     *
     * @param id restaurant id
     * @return {@link RestaurantDto} found restaurant
     */
    @Cacheable(value = "restaurants")
    @Override
    public RestaurantDto findById(int id) {
        log.info("Retrieving restaurant by id");
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id))));
    }

    /**
     * Find restaurant by and bind menu with votes by date
     *
     * @param date preferred date to find menu and votes
     * @param id   restaurant id
     * @return {@link RestaurantDto} found restaurant
     * @throws EntityNotFoundException if restaurant with such id not found
     */
    @Override
    public RestaurantDto findByIdWithMenuAndVotesForDate(int id, LocalDate date) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id)));
        Optional<Menu> byRestaurantIdByDate = menuRepository.findByRestaurantIdByDate(id, date);
        restaurant.setMenus(byRestaurantIdByDate.map(List::of).orElse(Collections.emptyList()));
        log.info("Retrieving restaurant id: {} with votes and menu by date {}", id, date);
        return mapper.entityToDtoWithMenus(restaurant);
    }

    /**
     * Update restaurant data
     *
     * @param id            restaurant id for update
     * @param restaurantDto dto with data to update
     * @return {@link RestaurantDto} updated restaurant
     * @throws EntityNotFoundException if restaurant with such id not found
     */
    @CacheEvict(value = "restaurants",allEntries = true)
    @Override
    @Transactional
    public RestaurantDto update(int id, InputRestaurantDto restaurantDto) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Restaurant with id: %s not found".formatted(id)));
        restaurant.setName(restaurantDto.name());
        Restaurant updatedRestaurant = repository.save(restaurant);
        log.info("Restaurant with id: {} updated", id);
        return mapper.entityToDto(updatedRestaurant);
    }

    /**
     * Delete restaurant
     *
     * @param id restaurant id
     */
    @CacheEvict(value = {"restaurants", "menus"}, allEntries = true)
    @Override
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
        log.info("Restaurant with id: {} deleted", id);
    }
}
