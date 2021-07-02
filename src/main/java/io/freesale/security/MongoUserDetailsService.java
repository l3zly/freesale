package io.freesale.security;

import io.freesale.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MongoUserDetailsService implements ReactiveUserDetailsService {

  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String id) {
    return userRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
        .map(SecurityUser::new);
  }

}
