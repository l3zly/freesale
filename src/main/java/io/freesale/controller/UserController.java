package io.freesale.controller;

import io.freesale.dto.CreateUserDto;
import io.freesale.dto.ErrorDto;
import io.freesale.dto.LoginDto;
import io.freesale.dto.TokenDto;
import io.freesale.dto.UserDto;
import io.freesale.exception.InvalidCredentialsException;
import io.freesale.security.SecurityUser;
import io.freesale.service.BalanceService;
import io.freesale.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final BalanceService balanceService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<TokenDto> signup(@RequestBody Mono<CreateUserDto> createUserDto) {
    return userService.signup(createUserDto);
  }

  @PostMapping("/login")
  public Mono<TokenDto> login(@RequestBody Mono<LoginDto> loginDto) {
    return userService.login(loginDto);
  }

  @GetMapping("/me")
  public Mono<UserDto> getMe(Mono<Authentication> authentication) {
    return authentication
        .map(auth -> ((SecurityUser) auth.getPrincipal()).getUser())
        .flatMap(user -> balanceService
            .calculateBalance(user.getId())
            .map(balance -> new UserDto(user.getId(), user.getPhone(), balance)));
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(InvalidCredentialsException.class)
  public ErrorDto handleInvalidCredentials(Exception e) {
    return new ErrorDto(e.getMessage());
  }

}
