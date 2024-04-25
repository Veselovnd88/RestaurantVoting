package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Vote;

@Mapper(config = MapStructConfig.class, uses = {UserMapper.class})
public interface VoteMapper {

    @Mapping(target = "votedAt", source = "votedAt")
    @Mapping(target = "restaurantId", source = "menu.restaurant.id")
    VoteDto toDto(Vote vote);
}
