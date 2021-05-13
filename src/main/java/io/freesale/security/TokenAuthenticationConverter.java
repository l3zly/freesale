package io.freesale.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class TokenAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
        return Mono
                .just(serverWebExchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION))
                .map(this::extractTokenFromAuthorizations)
                .map(token -> new PreAuthenticatedAuthenticationToken(null, token));
    }

    private String extractTokenFromAuthorizations(List<String> authorizations) {
        if (authorizations.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Authorization header is empty");
        }

        var tokenTypeAndToken = authorizations.get(0).split(" ");

        if (tokenTypeAndToken.length < 2) {
            throw new AuthenticationCredentialsNotFoundException("Authorization is formatted incorrectly");
        }

        return tokenTypeAndToken[1];
    }

}
