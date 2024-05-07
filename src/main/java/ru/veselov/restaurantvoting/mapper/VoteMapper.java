package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Vote;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface VoteMapper {

    @Mapping(target = "votedAt", source = "votedAt")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    VoteDto toDto(Vote vote);

    List<VoteDto> toDtos(List<Vote> votes);
}
