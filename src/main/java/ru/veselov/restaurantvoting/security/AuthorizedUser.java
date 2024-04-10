package ru.veselov.restaurantvoting.security;


import lombok.Getter;
import lombok.Setter;
import ru.veselov.restaurantvoting.model.User;

@Getter
@Setter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private int id;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.id = user.id();
    }
}
