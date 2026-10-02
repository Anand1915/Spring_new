package com.Day8.SpringSecurityApp.SpringSecurityApp.controllers;

import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginResponseDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.SignUpDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.UserDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.AuthService;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    @Value("${deploy.env}")

    private String deployEnv;


    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(
            @RequestBody SignUpDto signUpDto) {

        UserDto userDto =
                userService.signUp(signUpDto);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginDto loginDto,
            HttpServletResponse response) {

        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());

        cookie.setHttpOnly(true);

        cookie.setSecure("production".equals(deployEnv));

        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")

    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
     String refreshToken =   Arrays.stream(request.getCookies()).filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
             .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("refreshToken not found inside cookies"));

         LoginResponseDto loginResponseDto=   authService.refreshToken(refreshToken);

         return  ResponseEntity.ok(loginResponseDto);


    }


}