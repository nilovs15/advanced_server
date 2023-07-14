package com.example.advanced_server.security;

import java.io.IOException;
import java.util.Collections;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {


  private final JwtTokenProvider jwtTokenProvider;
  private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {

            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        catch (CustomException e){
            CustomSuccessResponse errorResponse = CustomSuccessResponse.getBadResponse(
                    Collections.singletonList(ErrorCodes.UNAUTHORISED.getErrorCode()),
                    ErrorCodes.UNAUTHORISED.getErrorCode());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(errorResponse));
            return;
        }
        chain.doFilter(request, response);
    }
}
