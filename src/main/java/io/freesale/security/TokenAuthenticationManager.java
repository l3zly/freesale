package io.freesale.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {

  private final TokenUtil tokenUtil;
  private final ReactiveUserDetailsService userDetailsService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    var token = authentication.getCredentials().toString();
    return Mono
        .just(token)
        .map(tokenUtil::verify)
        .flatMap(claims -> userDetailsService.findByUsername(claims.getSubject()))
        .onErrorMap(InvalidTokenException::new)
        .map(securityUser -> new UsernamePasswordAuthenticationToken(securityUser, token,
            securityUser.getAuthorities()));
  }

}
