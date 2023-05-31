package com.charapadev.blendsshop.security.filters;

import com.charapadev.blendsshop.security.authentications.DefaultAuthentication;
import com.charapadev.blendsshop.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @Nullable HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        checkNullableParameters(response);

        String authHeader = request.getHeader("Authorization");
        String jwtToken = extractJwtFromHeader(authHeader);

        Claims claims = jwtHelper.decode(jwtToken);
        String username = claims.get("username", String.class);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));

        Authentication auth = new DefaultAuthentication(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/products");
    }

    private String extractJwtFromHeader(String header) throws BadCredentialsException {
        String BEARER_PREFIX = "Bearer ";

        if (header == null) {
            throw new BadCredentialsException("Token not provided in Authorization header!");
        }

        if (!header.startsWith(BEARER_PREFIX)) {
            throw new BadCredentialsException("The token provided is not a Bearer token!");
        }

        return header.substring(BEARER_PREFIX.length());
    }

    private void checkNullableParameters(HttpServletResponse response) throws RuntimeException {
        if (response == null) {
            throw new RuntimeException("Some of the parameters on doFilterInternal is null");
        }
    }
}
