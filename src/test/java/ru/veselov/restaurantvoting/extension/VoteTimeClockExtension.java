package ru.veselov.restaurantvoting.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.veselov.restaurantvoting.service.VoteServiceImpl;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * Extension adjust mock of Clock and set up {@link VoteServiceImpl} with timeLimit field and clock fields
 */
public class VoteTimeClockExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Object testInstance = extensionContext.getRequiredTestInstance();
        AdjustClockMockForVoting adjustClockMockForVoting = AnnotationSupport
                .findAnnotation(extensionContext.getRequiredTestMethod(), AdjustClockMockForVoting.class).orElse(null);
        if (adjustClockMockForVoting == null) {
            return;
        }
        boolean exceeds = adjustClockMockForVoting.exceeds();
        Clock clock = Mockito.mock(Clock.class);
        if (exceeds) {
            Mockito.when(clock.instant())
                    .thenReturn(LocalDateTime.of(VoteTestData.VOTED_AT_DATE, LocalTime.of(23, 30, 0)).toInstant(ZoneOffset.UTC));
        } else {
            Mockito.when(clock.instant())
                    .thenReturn(LocalDateTime.of(VoteTestData.VOTED_AT_DATE, LocalTime.of(22, 0, 0)).toInstant(ZoneOffset.UTC));
        }
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        Field resultField = testInstance.getClass().getDeclaredField("voteService");
        resultField.setAccessible(true);
        VoteServiceImpl voteService = (VoteServiceImpl) resultField.get(testInstance);
        ReflectionTestUtils.setField(voteService, "clock", clock, Clock.class);
        ReflectionTestUtils.setField(voteService, "limitTime", LocalTime.of(23, 0, 0), LocalTime.class);
    }
}
