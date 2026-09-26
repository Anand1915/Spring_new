package com.Day8.SpringSecurityApp.SpringSecurityApp.filters;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.JwtService;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class jwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String requestTokenHeader =
                request.getHeader("Authorization");

        if (requestTokenHeader == null ||
                !requestTokenHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenHeader.substring(7);

        Long userId = jwtService.getUserIdFromToken(token);

        if (userId != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.getUserById(userId);

            UsernamePasswordAuthenticationToken
                    usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(
                            usernamePasswordAuthenticationToken
                    );
        }

        filterChain.doFilter(request, response);
    }
}