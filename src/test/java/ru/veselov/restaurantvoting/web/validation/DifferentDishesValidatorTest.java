package ru.veselov.restaurantvoting.web.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.veselov.restaurantvoting.dto.DishDto;
import ru.veselov.restaurantvoting.extension.ValidationContextMockResolver;
import ru.veselov.restaurantvoting.util.DishTestData;

import java.util.List;

@ExtendWith({MockitoExtension.class, ValidationContextMockResolver.class})
class DifferentDishesValidatorTest {

    DifferentDishesValidator differentDishesValidator = new DifferentDishesValidator();

    ConstraintValidatorContext constraintContext;

    public DifferentDishesValidatorTest(ConstraintValidatorContext constraintContext) {
        this.constraintContext = constraintContext;
    }

    @Test
    void isValid_DifferentDishes_ReturnTrue() {
        List<DishDto> dishes = List.of(DishTestData.philadelphiaDto, DishTestData.tastyRollDto);
        Assertions.assertThat(differentDishesValidator.isValid(dishes, constraintContext)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isValid_NullOrEmpty_ReturnTrue(List<DishDto> dishes) {
        Assertions.assertThat(differentDishesValidator.isValid(dishes, constraintContext)).isTrue();
    }

    @Test
    void isValid_SimilarDishes_ReturnFalse() {
        List<DishDto> dishes = List.of(DishTestData.philadelphiaDto, DishTestData.philadelphiaDto);
        Assertions.assertThat(differentDishesValidator.isValid(dishes, constraintContext)).isFalse();
    }
}