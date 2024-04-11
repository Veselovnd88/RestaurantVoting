package ru.veselov.restaurantvoting.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.veselov.restaurantvoting.exception.ErrorCode;
import ru.veselov.restaurantvoting.exception.ObjectAlreadyExistsException;
import ru.veselov.restaurantvoting.exception.VotingTimeLimitExceedsException;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final String LIMIT_FOR_VOTING_EXCEEDED = "Time limit for voting exceeded";
    public static final String OBJECT_ALREADY_EXISTS = "Object already exists";

    public static final String ERROR_CODE = "errorCode";
    public static final String TIMESTAMP = "timestamp";


    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.NOT_FOUND, e);
        problemDetail.setTitle(OBJECT_NOT_FOUND);
        problemDetail.setProperty(ERROR_CODE, ErrorCode.NOT_FOUND.toString());
        log.warn(createLogMessageWithCauseAndLine(e));
        return problemDetail;
    }

    @ExceptionHandler(VotingTimeLimitExceedsException.class)
    public ProblemDetail handleVotingTimeLimitExceeds(VotingTimeLimitExceedsException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.BAD_REQUEST, e);
        problemDetail.setTitle(LIMIT_FOR_VOTING_EXCEEDED);
        problemDetail.setProperty(ERROR_CODE, ErrorCode.BAD_REQUEST.toString());
        createLogMessageWithCauseAndLine(e);
        return problemDetail;
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ProblemDetail handleObjectAlreadyExists(ObjectAlreadyExistsException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.CONFLICT, e);
        problemDetail.setTitle(OBJECT_ALREADY_EXISTS);
        problemDetail.setProperty(ERROR_CODE, ErrorCode.CONFLICT.toString());
        createLogMessageWithCauseAndLine(e);
        return problemDetail;
    }

    private static String createLogMessageWithCauseAndLine(Exception e) {
        return e.getMessage().concat("; caused here: ").concat(e.getStackTrace()[0].toString());
    }

    private ProblemDetail createProblemDetail(HttpStatus status, Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }
}
