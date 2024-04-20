package ru.veselov.restaurantvoting.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.veselov.restaurantvoting.dto.ViolationError;
import ru.veselov.restaurantvoting.exception.ErrorCode;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;
import ru.veselov.restaurantvoting.util.ValidationUtil;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
@Slf4j
public class GlobalExceptionHandler {
    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final String LIMIT_FOR_VOTING_EXCEEDED = "Time limit for voting exceeded";
    public static final String OBJECT_ALREADY_EXISTS = "Object already exists";

    public static final String ERROR_CODE = "errorCode";
    public static final String TIMESTAMP = "timestamp";

    public static final String UNIQUE_DISH_MENU_CONSTRAINT = "name_menu_idx";
    public static final String UNIQUE_MENU_RESTAURANT_DATE_CONSTRAINT = "restaurant_day_idx";
    public static final String DISH_MENU_EXISTS = "Dish with such name already exists in menu";
    public static final String MENU_FOR_RESTAURANT_FOR_DATE_EXISTS = "Menu for this restaurant for date already exists";


    public static final Map<String, String> CONSTRAINTS_MAP = Map.of(
            UNIQUE_DISH_MENU_CONSTRAINT, DISH_MENU_EXISTS,
            UNIQUE_MENU_RESTAURANT_DATE_CONSTRAINT, MENU_FOR_RESTAURANT_FOR_DATE_EXISTS
    );
    public static final String VALIDATION_ERROR = "Validation error";
    public static final String VIOLATIONS = "violations";
    public static final String FIELDS_VALIDATION_FAILED = "Fields validation failed";

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException e) {
        return createProblemDetail(req, HttpStatus.NOT_FOUND, e,
                OBJECT_NOT_FOUND, Map.of(ERROR_CODE, ErrorCode.NOT_FOUND.name()), null, true);
    }

    @ExceptionHandler(VotingTimeLimitExceedsException.class)
    public ProblemDetail handleVotingTimeLimitExceeds(HttpServletRequest req, VotingTimeLimitExceedsException e) {
        return createProblemDetail(req, HttpStatus.BAD_REQUEST, e,
                LIMIT_FOR_VOTING_EXCEEDED, Map.of(ERROR_CODE, ErrorCode.BAD_REQUEST.name()), null, true);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        final List<ViolationError> violationErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ViolationError(
                        error.getField(), error.getDefaultMessage(),
                        formatValidationCurrentValue(error.getRejectedValue())))
                .toList();
        return createProblemDetail(req, HttpStatus.UNPROCESSABLE_ENTITY, e, VALIDATION_ERROR,
                Map.of(ERROR_CODE, ErrorCode.VALIDATION.name(),
                        VIOLATIONS, violationErrors),
                FIELDS_VALIDATION_FAILED, false);
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        String lowerCaseMsg = rootMsg.toLowerCase();
        for (Map.Entry<String, String> entry : CONSTRAINTS_MAP.entrySet()) {
            if (lowerCaseMsg.contains(entry.getKey())) {
                return createProblemDetail(req, HttpStatus.CONFLICT, e,
                        OBJECT_ALREADY_EXISTS, Map.of(ERROR_CODE, ErrorCode.CONFLICT.name()), entry.getValue(), true);
            }
        }
        return createProblemDetail(req, HttpStatus.CONFLICT, e, OBJECT_NOT_FOUND,
                Map.of(ERROR_CODE, ErrorCode.CONFLICT.name()), rootMsg, true);
    }

    private ProblemDetail createProblemDetail(HttpServletRequest req, HttpStatus status, Exception e,
                                              String title, Map<String, Object> props, @Nullable String detail,
                                              boolean isLogging) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        String detailMsg = detail == null ? rootCause.getMessage() : detail;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detailMsg);
        problemDetail.setInstance(URI.create(req.getRequestURL().toString()));
        for (Map.Entry<String, Object> entry : props.entrySet()) {
            problemDetail.setProperty(entry.getKey(), entry.getValue());
        }
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        problemDetail.setTitle(title);
        if (isLogging) {
            log.warn(detailMsg.concat("; caused here: ").concat(rootCause.getStackTrace()[0].toString()));
        }
        return problemDetail;
    }

    private String formatValidationCurrentValue(Object object) {
        if (object == null) {
            return "null";
        }
        if (object.toString().contains(object.getClass().getName())) {
            return object.getClass().getSimpleName();
        }
        if (object instanceof Collection<?>) {
            return "Collection elements validation failed";
        }
        return object.toString();
    }
}
