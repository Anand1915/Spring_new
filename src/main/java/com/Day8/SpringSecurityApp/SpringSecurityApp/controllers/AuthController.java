package com.Day8.SpringSecurityApp.SpringSecurityApp.controllers;

import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.LoginDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.SignUpDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.dtos.UserDto;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.AuthService;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

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
    public ResponseEntity<String> login(
            @RequestBody LoginDto loginDto,
            HttpServletResponse response) {

        String token = authService.login(loginDto);

        Cookie cookie = new Cookie("token", token);

        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }
}