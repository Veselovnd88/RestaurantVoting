package ru.veselov.restaurantvoting.extension;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark test method for adjusting mock of clock object with
 * {@link VoteTimeClockExtension}
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(VoteTimeClockExtension.class)
public @interface AdjustClockMockForVoting {

    boolean exceeds() default false;
}
