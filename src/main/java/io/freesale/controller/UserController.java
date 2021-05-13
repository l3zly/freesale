package io.freesale.controller;

import io.freesale.dto.CreateUserDto;
import io.freesale.dto.TokenDto;
import io.freesale.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TokenDto> signup(@RequestBody Mono<CreateUserDto> createUserDto) {
        return userService.signup(createUserDto);
    }

}
