package io.freesale.service;

import io.freesale.dto.CreateUserDto;
import io.freesale.dto.TokenDto;
import io.freesale.model.User;
import io.freesale.repository.UserRepository;
import io.freesale.security.TokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final TokenUtil tokenUtil;

  public UserService(UserRepository repository, PasswordEncoder passwordEncoder,
      TokenUtil tokenUtil) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.tokenUtil = tokenUtil;
  }

  public Mono<TokenDto> signup(Mono<CreateUserDto> createUserDto) {
    return createUserDto
        .map(dto -> User.of(dto.getPhone(), passwordEncoder.encode(dto.getPassword())))
        .flatMap(repository::save)
        .map(user -> tokenUtil.generateToken(user.getId()))
        .map(accessToken -> new TokenDto(accessToken, "Bearer"));
  }

}
