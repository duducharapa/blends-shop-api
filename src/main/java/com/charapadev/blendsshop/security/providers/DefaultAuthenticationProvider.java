package com.charapadev.blendsshop.security.providers;

import com.charapadev.blendsshop.security.CustomUserDetails;
import com.charapadev.blendsshop.security.JpaUserDetailsService;
import com.charapadev.blendsshop.security.authentications.DefaultAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails userFound = findUser(username);
        boolean matches = passwordMatches(password, userFound.getPassword());

        if (matches) {
            return new DefaultAuthentication(username, password);
        } else {
            throw new BadCredentialsException("Bad credentials");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DefaultAuthentication.class.isAssignableFrom(authentication);
    }

    private CustomUserDetails findUser(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
