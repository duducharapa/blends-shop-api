package com.charapadev.blendsshop.security.filters;

import com.charapadev.blendsshop.security.authentications.DefaultAuthentication;
import com.charapadev.blendsshop.utils.JwtHelper;
import com.charapadev.blendsshop.utils.Route;
import com.charapadev.blendsshop.utils.RouteMatcher;
import com.charapadev.blendsshop.utils.Routes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class DefaultAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain
    ) {
        checkNullableParameters(response);

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        if (password == null || username == null) {
            throw new RuntimeException("Credentials cannot be null");
        }

        DefaultAuthentication auth = new DefaultAuthentication(username, password);
        authenticationManager.authenticate(auth);

        String token = jwtHelper.generate(username);
        response.setHeader("Authorization", token);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Route actualRoute = new Route(request.getMethod(), request.getServletPath());
        return !RouteMatcher.match(Routes.REQUEST_AUTH_TOKEN, actualRoute);
    }

    private void checkNullableParameters(HttpServletResponse response) {
        if (response == null) {
            throw new RuntimeException("Some of the parameters on doFilterInternal is null");
        }
    }
}
