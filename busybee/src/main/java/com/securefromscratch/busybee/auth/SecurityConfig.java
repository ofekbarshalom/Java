package com.securefromscratch.busybee.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .requiresChannel(channel -> channel
                .anyRequest()
                .requiresSecure()  // Force HTTPS for all requests
            )
            .headers(headers -> headers
                .httpStrictTransportSecurity()  // Enable HSTS header
            )
            .authorizeHttpRequests(auth -> auth
                // Allow access to the login page and its resources
                .requestMatchers(
                    "/",                // Root URL
                    "/index.html",      // The login page itself 
                    "/register",        // Registration endpoint (new)
                    "/register.js",     // Registration logic 
                    "/helpers.js",      // Helper functions
                    "/welcome.css",     // Login page styling
                    "/gencsrftoken",    // CSRF token endpoint
                    "/*.webp",          // Images like busybee.webp 
                    "/*.png",           // Logos
                    "/*.jpg",           // Icons
                    "/*.ico"            // Favicon 
                ).permitAll()           // "Permit All" = No password needed

                // Everything else requires authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main/main.html", true) // Redirect to the app after success 
                .permitAll()
            )
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );

        return http.build();
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}