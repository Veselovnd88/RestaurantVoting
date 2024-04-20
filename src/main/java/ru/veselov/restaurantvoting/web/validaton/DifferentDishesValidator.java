package ru.veselov.restaurantvoting.web.validaton;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.veselov.restaurantvoting.dto.DishDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class DifferentDishesValidator implements ConstraintValidator<DifferentDishes, List<DishDto>> {

    public static final String CONSTRAINT_MESSAGE = "Dishes names should be different in one menu, duplicates: %s";


    @Override
    public void initialize(DifferentDishes constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<DishDto> value, ConstraintValidatorContext context) {
        log.debug("Validation for different dishes");
        if (value == null || value.isEmpty()) {
            return true; //checked by another annotations
        }
        Set<String> uniques = new HashSet<>();
        Set<String> duplicates = value.stream().map(DishDto::name)
                .filter(name -> !uniques.add(name))
                .collect(Collectors.toSet());
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(CONSTRAINT_MESSAGE.formatted(StringUtils.join(duplicates, ",")))
                .addConstraintViolation();
        return duplicates.isEmpty();
    }
}
