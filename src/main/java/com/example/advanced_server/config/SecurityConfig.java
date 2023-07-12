package com.example.advanced_server.config;

import com.example.advanced_server.security.JwtConfig;
import com.example.advanced_server.security.JwtTokenProvider;
import com.example.advanced_server.security.Roles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

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
                .antMatchers(EndPoints.USER_ENDPOINT).hasAuthority(Roles.USER.getAuthority())
                .anyRequest().authenticated()
                .and().apply(new JwtConfig(jwtTokenProvider));
    }
}
