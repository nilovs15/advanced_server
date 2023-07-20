package com.example.advanced_server.config;

import com.example.advanced_server.security.JwtConfig;
import com.example.advanced_server.security.JwtTokenProvider;
import com.example.advanced_server.security.Roles;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(EndPoints.LOGIN_ENDPOINT).permitAll()
                .antMatchers(EndPoints.NEWS_ENDPOINT).permitAll()
                .antMatchers(EndPoints.USER_ENDPOINT).hasAuthority(Roles.USER.getAuthority())
                .antMatchers(EndPoints.FILE_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and().apply(new JwtConfig(jwtTokenProvider));
    }
}
