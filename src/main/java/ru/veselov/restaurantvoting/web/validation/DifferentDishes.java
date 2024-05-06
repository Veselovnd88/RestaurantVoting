package ru.veselov.restaurantvoting.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.lang.NonNull;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.RECORD_COMPONENT, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentDishesValidator.class)
@Documented
public @interface DifferentDishes {

    @NonNull String message() default "Dishes names should be different in one menu, duplicates: %s";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
