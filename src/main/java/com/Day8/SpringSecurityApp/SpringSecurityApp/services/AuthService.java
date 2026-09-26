package com.Day8.SpringSecurityApp.SpringSecurityApp.services;

import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public String login(LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        User user = (User) authentication.getPrincipal();
       //AccessToken
        return jwtService.generateToken(user);
    }
}