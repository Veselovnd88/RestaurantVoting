package ru.veselov.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.veselov.restaurantvoting.service.VoteService;

import java.time.LocalDate;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Voting management", description = "Accept votes")
public class VoteController {

    public static final String REST_URL = "/api/v1/voting";

    private final VoteService service;

    @Operation(summary = "Here user can vote for chosen menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vote accepted")})
    @PostMapping("/menus/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable("menuId") int menuId) {
        service.vote(100000, menuId, LocalDate.now());
    }

    @Operation(summary = "Remove user's vote for today")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vote removed")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeVote() {
        service.removeVote(100000, LocalDate.now());
    }
}
