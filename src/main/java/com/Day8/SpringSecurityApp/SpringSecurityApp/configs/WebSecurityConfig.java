package com.Day8.SpringSecurityApp.SpringSecurityApp.configs;

import com.Day8.SpringSecurityApp.SpringSecurityApp.filters.jwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final jwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityfilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/posts",
                                "/error",
                                "/auth/**"
                        ).permitAll()

                        .requestMatchers("/posts/*")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                );

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}