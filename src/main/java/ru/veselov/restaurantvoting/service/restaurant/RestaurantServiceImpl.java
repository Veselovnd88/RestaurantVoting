package ru.veselov.restaurantvoting.service.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veselov.restaurantvoting.dto.InputRestaurantDto;
import ru.veselov.restaurantvoting.dto.RestaurantDto;
import ru.veselov.restaurantvoting.exception.RestaurantNotFoundException;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.model.Restaurant;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    public static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

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
        return mapper.entityToDto(savedRestaurant);
    }

    /**
     * Get all restaurants with names and ids
     *
     * @return {@link RestaurantDto} list of dtos
     */
    @Cacheable(value = "restaurants")
    @Override
    public List<RestaurantDto> getAll() {
        log.info("Retrieving restaurants from repository");
        return mapper.entitiesToDto(repository.findAll(SORT_BY_NAME));
    }

    /**
     * Get restaurant by id
     *
     * @param id restaurant id
     * @return {@link RestaurantDto} found restaurant
     * @throws RestaurantNotFoundException if restaurant not found by id
     */
    @Cacheable(value = "restaurants")
    @Override
    public RestaurantDto findById(int id) {
        log.info("Retrieving restaurant by id");
        return mapper.entityToDto(getRestaurantById(id));
    }

    /**
     * Find restaurant with menu by date
     *
     * @param date preferred date to find menu
     * @param id   restaurant id
     * @return {@link RestaurantDto} found restaurant
     * @throws RestaurantNotFoundException if restaurant with such id not found
     */
    @Override
    public RestaurantDto findByIdWithMenuForDate(int id, LocalDate date) {
        Restaurant restaurant = repository.findByIdWithMenuByDate(id, date)
                .orElseThrow(() -> new RestaurantNotFoundException(id, date));
        log.info("Retrieving restaurant id: {} with votes and menu by date {}", id, date);
        return mapper.entityToDtoWithMenus(restaurant);
    }

    /**
     * Find restaurants with menus by date
     *
     * @param date preferred date to find menu
     * @return {@link List<RestaurantDto>} found restaurants
     */
    @Override
    public List<RestaurantDto> findAllWithMenuByDate(LocalDate date) {
        List<Restaurant> allRestaurantsWithMenu = repository.findAllWithMenuByDate(date, SORT_BY_NAME);
        log.info("Retrieving restaurants with menus by date {}", date);
        return mapper.entitiesToDtoWithMenus(allRestaurantsWithMenu);
    }

    /**
     * Update restaurant data
     *
     * @param id            restaurant id for update
     * @param restaurantDto dto with data to update
     * @return {@link RestaurantDto} updated restaurant
     * @throws RestaurantNotFoundException if restaurant with such id not found
     */
    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    @Transactional
    public RestaurantDto update(int id, InputRestaurantDto restaurantDto) {
        Restaurant restaurant = getRestaurantById(id);
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

    @Override
    public Restaurant getRestaurantById(int id) {
        return repository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
