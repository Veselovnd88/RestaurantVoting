package ru.veselov.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.security.AuthorizedUser;
import ru.veselov.restaurantvoting.service.VoteService;

import java.time.Clock;
import java.time.LocalDate;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Voting management", description = "Accept votes")
public class VoteController {

    public static final String REST_URL = "/api/v1/votes";

    private final VoteService service;
    private final Clock clock;

    @Operation(summary = "Here user can vote for chosen restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vote accepted")})
    @PostMapping("/menus/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        service.vote(user.getId(), restaurantId, LocalDate.now(clock));
    }

    @Operation(summary = "Remove user's vote for today")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vote removed")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeVote(@AuthenticationPrincipal AuthorizedUser user) {
        service.removeVote(user.getId(), LocalDate.now(clock));
    }
}
