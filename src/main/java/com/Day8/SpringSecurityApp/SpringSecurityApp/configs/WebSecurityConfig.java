package com.Day8.SpringSecurityApp.SpringSecurityApp.configs;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Permission;
import com.Day8.SpringSecurityApp.SpringSecurityApp.filters.jwtAuthFilter;
import com.Day8.SpringSecurityApp.SpringSecurityApp.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Role.ADMIN;
import static com.Day8.SpringSecurityApp.SpringSecurityApp.entites.enums.Role.CREATOR;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final jwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error",
            "/auth/**",
            "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .oauth2Login(oauth2Config ->
                        oauth2Config
                                .failureUrl("/login?error=true")
                                .successHandler(oAuth2SuccessHandler)
                )

                .authorizeHttpRequests(auth -> auth

                        // Public routes
                        .requestMatchers(publicRoutes)
                        .permitAll()

                        // GET /posts/**
                        .requestMatchers(
                                HttpMethod.GET,
                                "/posts/**"
                        )
                        .permitAll()

                        // POST /posts/**
                        .requestMatchers(
                                HttpMethod.POST,
                                "/posts/**"
                        )
                        .hasAnyRole(

                                ADMIN.name(),
                                CREATOR.name()
                        )

                        // PUT /posts/**
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/posts/**"
                        )
                        .hasAuthority(
                                Permission.Post_Update.name()
                        )

                        // DELETE /posts/**
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/posts/**"
                        )
                        .hasAuthority(
                                Permission.Post_Delete.name()
                        )

                        // Everything else
                        .anyRequest()
                        .authenticated()
                );

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }
}