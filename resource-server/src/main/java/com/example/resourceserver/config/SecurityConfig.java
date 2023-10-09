package com.example.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain supportSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/support/card") // Only filter for this path
                .authorizeHttpRequests(c -> c.anyRequest().hasAuthority("SCOPE_support"))
                .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/articles").permitAll()
                        .requestMatchers("/overview").permitAll()
                        .requestMatchers("/purchase").hasAuthority("PURCHASE_ALLOWED")
                        .anyRequest().authenticated()
                )
                .build();
    }
}
