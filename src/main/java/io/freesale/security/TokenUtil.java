package io.freesale.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
public class TokenUtil {

    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public String generateToken(String subject) {
        return Jwts
                .builder()
                .setSubject(subject)
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public Claims verify(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
