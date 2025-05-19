package com.example.wodozbior.service;

import com.example.wodozbior.dto.auth.AccessTokenDTO;
import com.example.wodozbior.dto.auth.LoginRequestDTO;
import com.example.wodozbior.dto.auth.RegisterRequestDTO;
import com.example.wodozbior.entity.User;
import com.example.wodozbior.enums.Role;
import com.example.wodozbior.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    public ResponseEntity<AccessTokenDTO> register(RegisterRequestDTO dto, HttpServletResponse response) {
    if (userService.existsByEmail(dto.email())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null); // lub nowy DTO z komunikatem, jeśli chcesz
    }

    User user = new User();
    user.setEmail(dto.email());
    user.setPassword(passwordService.encodePassword(dto.password()));
    user.setFirstName(dto.firstName());
    user.setLastName(dto.lastName());
    user.setNativeLanguage(dto.nativeLanguage());
    user.setRole(Role.USER);
    user.setActive(true);
    user.setCreatedAt(LocalDateTime.now());
    user.setRecentDocumentIds(Collections.emptyList());

    userService.registerUser(user);

    String accessToken = jwtUtil.generateAccessToken(user.getId());
    String refreshToken = jwtUtil.generateRefreshToken(user.getId());

    addCookieToResponse("refresh_token", refreshToken, response);

    return ResponseEntity.ok(new AccessTokenDTO(accessToken));
}


    public ResponseEntity<AccessTokenDTO> login(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        String email = loginRequestDTO.email();
        String password = loginRequestDTO.password();

        if (userService.isValidUser(email, password)) {
            User user = userService.findByEmail(email).orElseThrow(); // Zwraca obiekt użytkownika z ID
            String accessToken = jwtUtil.generateAccessToken(user.getId()); // <-- TU ID!
            String refreshToken = jwtUtil.generateRefreshToken(user.getId());

            addCookieToResponse("refresh_token", refreshToken, response);

            return ResponseEntity.ok(new AccessTokenDTO(accessToken));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<AccessTokenDTO> refresh(String refreshToken, HttpServletResponse response) {
        if (jwtUtil.validateToken(refreshToken)) {
            String userId = jwtUtil.extractUserId(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(userId);
            String newRefreshToken = jwtUtil.generateRefreshToken(userId);

            addCookieToResponse("refresh_token", newRefreshToken, response);

            return ResponseEntity.ok(new AccessTokenDTO(newAccessToken));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // ZMIEŃ NA true w produkcji
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    private void addCookieToResponse(String cookieName, String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // ZMIEŃ NA `true` w środowisku produkcyjnym!
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 3600); // 7 dni
        response.addCookie(cookie);
    }
}
