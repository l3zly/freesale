package io.freesale.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class TokenAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenUtil tokenUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public TokenAuthenticationManager(TokenUtil tokenUtil, ReactiveUserDetailsService userDetailsService) {
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        var token = authentication.getCredentials().toString();
        return Mono
                .just(tokenUtil.verify(token))
                .flatMap(claims -> userDetailsService.findByUsername(claims.getSubject()))
                .map(securityUser -> new UsernamePasswordAuthenticationToken(securityUser, token,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

}
