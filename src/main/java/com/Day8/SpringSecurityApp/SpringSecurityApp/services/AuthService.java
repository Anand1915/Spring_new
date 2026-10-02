package com.Day8.SpringSecurityApp.SpringSecurityApp.services;

import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginResponseDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.Session;
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

    private  final  UserService userService;

    private  final  SessionService sessionService;


    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        User user = (User) authentication.getPrincipal();
       //AccessToken
        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user,refreshToken);


        return  new LoginResponseDto(user.getId(),accessToken, refreshToken);


    }

    public LoginResponseDto refreshToken(String refreshToken) {
         Long userId = jwtService.getUserIdFromToken(refreshToken);
              sessionService.validateSession(refreshToken);
         User user = userService.getUserById(userId);



        String accessToken = jwtService.generateAccessToken(user);

        return  new LoginResponseDto(user.getId(),accessToken, refreshToken);


    }
}