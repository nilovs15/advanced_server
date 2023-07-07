package com.example.advanced_server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    @Value("${jwt_secret}")
    private String secret;
    public String generateToken(String email) {
        return JWT.create()
                .withClaim("email", email)
                .sign(Algorithm.HMAC256(secret));
    }
}
