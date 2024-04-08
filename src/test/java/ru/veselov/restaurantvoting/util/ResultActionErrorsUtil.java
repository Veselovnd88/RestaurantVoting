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
    private static final String JSON_ERROR_CODE = "$.properties." + GlobalExceptionHandler.ERROR_CODE;

    private static final String JSON_TIMESTAMP = "$.properties." + GlobalExceptionHandler.TIMESTAMP;

    private static final String JSON_VIOLATIONS_FIELD = "$.violations[%s].name";

    private static final String JSON_TITLE = "$.title";

    private static final String JSON_DETAIL = "$.detail";

    public static void checkNotFoundFields(ResultActions resultActions, String detail) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE)
                        .value(ErrorCode.NOT_FOUND.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TIMESTAMP).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE).value(GlobalExceptionHandler.OBJECT_NOT_FOUND))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL, Matchers.startsWith(detail)));
    }

    public static void checkVoteLimitExceedError(ResultActions resultActions, String detail) throws Exception {
        checkProblemJsonCompatibility(resultActions);
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_ERROR_CODE)
                        .value(ErrorCode.BAD_REQUEST.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TIMESTAMP).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_TITLE)
                        .value(GlobalExceptionHandler.LIMIT_FOR_VOTING_EXCEEDED))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_DETAIL, Matchers.startsWith(detail)));
    }

    private static void checkProblemJsonCompatibility(ResultActions resultActions) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON_VALUE));
    }
}
