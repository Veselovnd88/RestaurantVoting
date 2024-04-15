package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.Vote;

@Mapper(config = MapStructConfig.class)
public interface VoteMapper {

    @Mapping(target = "votedAt", source = "votedAt")
    @Mapping(target = "user.id", source = "user.id")
    @Mapping(target = "user.email", source = "user.email")
    @Mapping(target = "user.name", source = "user.name")
    VoteDto toDto(Vote vote);
}
