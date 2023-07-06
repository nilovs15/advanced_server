package com.example.advanced_server.config;

import java.io.IOException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.PersonDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository, PersonDetailsService personDetailsService, PersonDetailsService personDetailsService1) {
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authHeader = request.getHeader("Authorization");
       if (authHeader != null && authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
           String jwt = authHeader.substring(7);

           if (jwt.isBlank()) {
               response.sendError(HttpServletResponse.SC_BAD_REQUEST, ValidationConstants.TOKEN_NOT_PROVIDED);
           }
           else {
               try {
                   String email = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                   UserDetails userDetails = personDetailsService.loadUserByUsername(email);

                   UsernamePasswordAuthenticationToken authToken =
                           new UsernamePasswordAuthenticationToken(userDetails.getPassword(),
                                   userDetails.getAuthorities());
                   if (SecurityContextHolder.getContext().getAuthentication() == null) {
                       SecurityContextHolder.getContext().setAuthentication(authToken);
                   }
               }
               catch (JWTVerificationException e) {
                   response.sendError(HttpServletResponse.SC_BAD_REQUEST, ValidationConstants.TOKEN_POSITION_MISMATCH);
               }
           }
       }
       filterChain.doFilter(request, response);
    }
}
