package ru.veselov.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.veselov.restaurantvoting.exception.ErrorCode;
import ru.veselov.restaurantvoting.web.GlobalExceptionHandler;

@UtilityClass
public class ResultActionErrorsUtil {
    private static final String JSON_ERROR_CODE = "$." + GlobalExceptionHandler.ERROR_CODE;

    private static final String JSON_TIMESTAMP = "$." + GlobalExceptionHandler.TIMESTAMP;

    private static final String JSON_VIOLATIONS_FIELD = "$.violations[%s].name";

    private static final String JSON_VIOLATIONS_MESSAGE = "$.violations[%s].message";

    private static final String JSON_TITLE = "$.title";

    private static final String JSON_DETAIL = "$.detail";

    public static final String JSON_INSTANCE = "$.instance";

    public static void checkNotFoundFields(ResultActions resultActions, String detail) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        checkTimeStampIsNotEmpty(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE)
                        .value(ErrorCode.NOT_FOUND.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE).value(GlobalExceptionHandler.OBJECT_NOT_FOUND))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL, Matchers.startsWith(detail)));
    }

    public static void checkVoteLimitExceedError(ResultActions resultActions, String detail) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        checkTimeStampIsNotEmpty(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE)
                        .value(ErrorCode.BAD_REQUEST.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE)
                        .value(GlobalExceptionHandler.LIMIT_FOR_VOTING_EXCEEDED))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL, Matchers.startsWith(detail)));
    }

    public static void checkConflictError(ResultActions resultActions, String detail, String url) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        checkTimeStampIsNotEmpty(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE).value(ErrorCode.CONFLICT.name()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE)
                        .value(GlobalExceptionHandler.OBJECT_ALREADY_EXISTS))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL).value(detail))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_INSTANCE, Matchers.endsWith(url)));
    }

    public static void checkValidationError(ResultActions resultActions, String detail, String url, String field,
                                            int fieldIndex) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        checkTimeStampIsNotEmpty(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE).value(ErrorCode.VALIDATION.name()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE)
                        .value(GlobalExceptionHandler.VALIDATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL, Matchers.startsWith(detail)))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_INSTANCE, Matchers.endsWith(url)))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_VIOLATIONS_FIELD.formatted(fieldIndex)).value(field))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_VIOLATIONS_MESSAGE.formatted(fieldIndex)).isNotEmpty());
    }

    public static void checkRequestValidationError(ResultActions resultActions, String detail, String url) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        checkTimeStampIsNotEmpty(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE).value(ErrorCode.VALIDATION.name()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE)
                        .value(GlobalExceptionHandler.VALIDATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL, Matchers.startsWith(detail)))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_INSTANCE, Matchers.endsWith(url)));
    }

    private static void checkProblemJsonCompatibility(ResultActions resultActions) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON_VALUE));
    }

    private static void checkTimeStampIsNotEmpty(ResultActions resultActions) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(JSON_TIMESTAMP).isNotEmpty());
    }
}
