package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.veselov.restaurantvoting.model.User;

@UtilityClass
public class SecurityUtils {

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }
}
