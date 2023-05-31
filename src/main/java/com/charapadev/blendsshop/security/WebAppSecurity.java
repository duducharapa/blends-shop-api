package com.charapadev.blendsshop.security;

import com.charapadev.blendsshop.security.filters.DefaultAuthenticationFilter;
import com.charapadev.blendsshop.security.filters.JwtAuthorizationFilter;
import com.charapadev.blendsshop.security.providers.DefaultAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebAppSecurity {

    @Lazy
    @Autowired
    private DefaultAuthenticationFilter defaultAuthenticationFilter;

    @Autowired
    private DefaultAuthenticationProvider defaultAuthenticationProvider;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.authenticationProvider(defaultAuthenticationProvider);
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterAt(defaultAuthenticationFilter, BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter.class);

        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());

        return http.build();
    }

}
