package com.charapadev.blendsshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfiguration {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        List<String> allowedMethods = Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS");

        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedOrigins(List.of("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
