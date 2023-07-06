package com.example.advanced_server.config;

import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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
}
