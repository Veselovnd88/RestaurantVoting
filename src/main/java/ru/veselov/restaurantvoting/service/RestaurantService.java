package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;
import ru.veselov.restaurantvoting.to.RestaurantTo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    public List<RestaurantTo> getAllWithVotes() {
        log.debug("Retrieving restaurants with votes from repository");
        return mapper.entitiesToDto(repository.findAllWithVotes());
    }

    public RestaurantTo findById(int id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id))));
    }
}
