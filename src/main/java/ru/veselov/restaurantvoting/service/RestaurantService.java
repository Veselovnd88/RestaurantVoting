package ru.veselov.restaurantvoting.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veselov.restaurantvoting.dto.RestaurantTo;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.repository.RestaurantRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    public List<RestaurantTo> getAll() {
        log.debug("Retrieving restaurants with votes from repository");
        return mapper.entitiesToDto(repository.findAll());
    }

    public RestaurantTo findByIdWithMenuAndVotes(int id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with [id:%s] not found".formatted(id))));
    }
}
