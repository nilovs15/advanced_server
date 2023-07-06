package com.example.advanced_server.config;

import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;
    public String generateToken(String email) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(5).toInstant());
        return JWT.create()
                .withSubject("Entity details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("nilov")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("Entity details")
                .withIssuer("nilov")
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
