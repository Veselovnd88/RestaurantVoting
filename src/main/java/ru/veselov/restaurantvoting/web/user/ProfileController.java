package ru.veselov.restaurantvoting.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.veselov.restaurantvoting.dto.UserDto;
import ru.veselov.restaurantvoting.security.AuthorizedUser;
import ru.veselov.restaurantvoting.service.user.UserService;
import ru.veselov.restaurantvoting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(ProfileController.REST_URL)
@Validated
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "User profile management", description = "User can manage his profile here")
public class ProfileController {

    public static final String REST_URL = "/api/v1/profile";

    private final UserService service;

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        ValidationUtil.checkNew(userDto);
        UserDto created = service.create(userDto);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Get authenticated user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get profile",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))})})
    @GetMapping
    public UserDto getProfile(@Schema(hidden = true) @AuthenticationPrincipal AuthorizedUser user) {
        return service.getById(user.getId());
    }

    @Operation(summary = "Update authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserDto userDto,
                       @Schema(hidden = true) @AuthenticationPrincipal AuthorizedUser authUser) {
        ValidationUtil.assureIdConsistent(userDto, authUser.getId());
        service.update(userDto);
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Schema(hidden = true) @AuthenticationPrincipal AuthorizedUser authUser) {
        service.deleteById(authUser.getId());
    }
}
