package com.charapadev.blendsshop.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DefaultAuthentication extends UsernamePasswordAuthenticationToken {

    public DefaultAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public DefaultAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
