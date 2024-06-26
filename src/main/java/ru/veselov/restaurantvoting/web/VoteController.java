package ru.veselov.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.veselov.restaurantvoting.dto.VoteDto;
import ru.veselov.restaurantvoting.security.AuthorizedUser;
import ru.veselov.restaurantvoting.service.vote.VoteService;

import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            @ApiResponse(responseCode = "201", description = "Vote created")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VoteDto> vote(@RequestParam("restaurantId") int restaurantId,
                                        @AuthenticationPrincipal AuthorizedUser user) {
        VoteDto created = service.vote(user.getId(), restaurantId, LocalDateTime.now(clock));
        URI uriOfResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VoteController.REST_URL)
                .buildAndExpand("/today").toUri();
        return ResponseEntity.created(uriOfResource).body(created);
    }

    @Operation(summary = "Here user can change his vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vote accepted")})
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeVote(@RequestParam("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser user) {
        service.changeVote(user.getId(), restaurantId, LocalDateTime.now(clock));
    }

    @Operation(summary = "Get today user's vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote retrieved")})
    @GetMapping("/today")
    public ResponseEntity<?> getTodayVote(@AuthenticationPrincipal AuthorizedUser user) {
        Optional<VoteDto> optionalVoteDto = service.getByUserIdForDate(user.getId(), LocalDate.now(clock));
        if (optionalVoteDto.isPresent()) {
            return ResponseEntity.ok(optionalVoteDto.get());
        } else return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all user's votes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Votes retrieved")})
    @GetMapping
    public List<VoteDto> getAll(@AuthenticationPrincipal AuthorizedUser user) {
        return service.getAllByUserId(user.getId());
    }
}
