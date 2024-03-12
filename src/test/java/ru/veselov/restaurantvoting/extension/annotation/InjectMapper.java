package ru.veselov.restaurantvoting.extension.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import ru.veselov.restaurantvoting.extension.MapperParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for injecting configured mappers to test classes
 *
 * @see MapperParameterResolver
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MapperParameterResolver.class)
public @interface InjectMapper {
}
