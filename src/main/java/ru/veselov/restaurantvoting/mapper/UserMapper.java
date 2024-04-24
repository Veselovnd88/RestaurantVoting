package ru.veselov.restaurantvoting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.mapper.config.MapStructConfig;
import ru.veselov.restaurantvoting.model.User;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", expression = "java(userDto.email().toLowerCase())")
    User toUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", expression = "java(userDto.email().toLowerCase())")
    void toUserUpdate(@MappingTarget User user, UserDto userDto);

    List<UserDto> toDtos(List<User> users);
}
