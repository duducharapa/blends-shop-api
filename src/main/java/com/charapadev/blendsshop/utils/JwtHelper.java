package com.charapadev.blendsshop.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String jwtKey;

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String username) {
        SecretKey key = generateKey();

        return Jwts.builder()
            .setClaims(Map.of("username", username))
            .signWith(key)
            .compact();
    }

    public Claims decode(String token) {
        SecretKey key = generateKey();

        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}
