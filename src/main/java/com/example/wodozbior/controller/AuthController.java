package com.example.wodozbior.controller;

import com.example.wodozbior.dto.auth.AccessTokenDTO;
import com.example.wodozbior.dto.auth.LoginRequestDTO;
import com.example.wodozbior.dto.auth.RegisterRequestDTO;
import com.example.wodozbior.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

@PostMapping("/register")
public ResponseEntity<AccessTokenDTO> register(@RequestBody RegisterRequestDTO dto, HttpServletResponse response) {
    return authService.register(dto, response);
}

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(@RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        return authService.login(dto, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDTO> refresh(
            @CookieValue(value = "refresh_token", defaultValue = "") String refreshToken,
            HttpServletResponse response) {
        return authService.refresh(refreshToken, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }
}
