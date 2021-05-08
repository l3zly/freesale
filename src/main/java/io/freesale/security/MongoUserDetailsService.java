package io.freesale.security;

import io.freesale.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public class MongoUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    public MongoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String phone) {
        return userRepository
                .findUserByPhone(phone)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(SecurityUser::new);
    }

}
