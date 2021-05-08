package io.freesale.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class TokenAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        return Mono
                .just(serverWebExchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION))
                .filter(authorizations -> !authorizations.isEmpty())
                .map(authorizations -> authorizations.get(0).split("Bearer ")[1])
                .map(token -> new PreAuthenticatedAuthenticationToken(null, token));
    }

}
