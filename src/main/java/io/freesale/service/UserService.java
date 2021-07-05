package io.freesale.service;

import io.freesale.dto.CreateUserDto;
import io.freesale.dto.LoginDto;
import io.freesale.dto.TokenDto;
import io.freesale.exception.InvalidCredentialsException;
import io.freesale.model.User;
import io.freesale.repository.UserRepository;
import io.freesale.security.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenUtil tokenUtil;

  public Mono<TokenDto> signup(Mono<CreateUserDto> createUserDto) {
    return createUserDto
        .map(dto -> User.of(dto.getPhone(), passwordEncoder.encode(dto.getPassword())))
        .flatMap(userRepository::save)
        .map(user -> tokenUtil.generateToken(user.getId()))
        .map(accessToken -> new TokenDto(accessToken, "Bearer"));
  }

  public Mono<TokenDto> login(Mono<LoginDto> loginDto) {
    return loginDto
        .flatMap(dto -> userRepository
            .findByPhone(dto.getPhone())
            .switchIfEmpty(Mono.error(InvalidCredentialsException::new))
            .handle((user, sink) -> {
              if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                sink.next(user);
              } else {
                sink.error(new InvalidCredentialsException());
              }
            })
            .cast(User.class)
            .map(user -> tokenUtil.generateToken(user.getId()))
            .map(accessToken -> new TokenDto(accessToken, "Bearer")));
  }

}
