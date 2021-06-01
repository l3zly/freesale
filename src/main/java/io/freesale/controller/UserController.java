package io.freesale.controller;

import io.freesale.dto.CreateUserDto;
import io.freesale.dto.TokenDto;
import io.freesale.dto.UserDto;
import io.freesale.security.SecurityUser;
import io.freesale.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

  @GetMapping("/me")
  public Mono<UserDto> getMe(Mono<Authentication> authentication) {
    return authentication
        .map(auth -> (SecurityUser) auth.getPrincipal())
        .map(securityUser -> new UserDto(securityUser.getUser().getId(),
            securityUser.getUser().getPhone()));
  }

}
