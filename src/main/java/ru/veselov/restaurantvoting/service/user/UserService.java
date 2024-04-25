package ru.veselov.restaurantvoting.service.user;

import ru.veselov.restaurantvoting.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    void update(UserDto userDto);

    void deleteById(int id);

    void enable(int id, boolean enabled);

    UserDto getById(int id);

    UserDto getByEmail(String email);

    List<UserDto> getAll();
}
