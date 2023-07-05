package com.example.advanced_server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

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
}
