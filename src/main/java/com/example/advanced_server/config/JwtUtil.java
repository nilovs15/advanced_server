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
public class JwtUtil {
    @Value("${jwt_secret}")
    private String secret;
    public String generateToken(String email) {
        return JWT.create()
                .withClaim("email", email)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) {
        JWTVerifier jwtVerifier = JWT
                .require(Algorithm.HMAC256(secret))
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
