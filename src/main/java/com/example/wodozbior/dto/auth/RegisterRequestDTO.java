package com.example.wodozbior.dto.auth;

public record RegisterRequestDTO(
        String email,
        String password,
        String firstName,
        String lastName,
        String nativeLanguage
) {
}
