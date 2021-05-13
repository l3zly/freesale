package io.freesale.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(Throwable cause) {
        super("Invalid token provided", cause);
    }

}
