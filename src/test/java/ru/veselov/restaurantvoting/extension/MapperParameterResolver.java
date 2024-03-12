package ru.veselov.restaurantvoting.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.extension.annotation.InjectMapper;
import ru.veselov.restaurantvoting.mapper.DishMapper;
import ru.veselov.restaurantvoting.mapper.DishMapperImpl;
import ru.veselov.restaurantvoting.mapper.MenuMapper;
import ru.veselov.restaurantvoting.mapper.MenuMapperImpl;
import ru.veselov.restaurantvoting.mapper.RestaurantMapper;
import ru.veselov.restaurantvoting.mapper.RestaurantMapperImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class define required annotated mapper and inject it in test class
 *
 * @see InjectMapper
 */
public class MapperParameterResolver implements ParameterResolver {

    private final Map<Class<?>, Object> availableMappers = new ConcurrentHashMap<>();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        setUpMappers();
        return parameterContext.isAnnotated(InjectMapper.class) &&
                availableMappers.containsKey(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return availableMappers.get(parameterContext.getParameter().getType());
    }

    private void setUpMappers() {
        DishMapper dishMapper = new DishMapperImpl();
        availableMappers.put(DishMapper.class, dishMapper);
        MenuMapper menuMapper = new MenuMapperImpl();
        ReflectionTestUtils.setField(menuMapper, "dishMapper", dishMapper, DishMapper.class);
        availableMappers.put(MenuMapper.class, menuMapper);
        RestaurantMapper restaurantMapper = new RestaurantMapperImpl();
        ReflectionTestUtils.setField(restaurantMapper, "menuMapper", menuMapper, MenuMapper.class);
        availableMappers.put(RestaurantMapper.class, restaurantMapper);
    }
}
