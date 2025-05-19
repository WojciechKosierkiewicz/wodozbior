package com.example.wodozbior.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;  // Klucz tajny, przechowywany w bezpieczny sposób (np. w zmiennych środowiskowych)

    private final long jwtExpirationInMs = 3600000;  // 1 godzina dla access token
    private final long refreshTokenExpirationInMs = 2592000000L;  // 30 dni dla refresh token

    // Generowanie Access Token - używamy tylko userId
    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)  // Używamy tylko userId w tokenie
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Generowanie Refresh Token
    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)  // Używamy tylko userId w tokenie
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Wyciąganie Claims (informacji) z tokenu
    public Claims extractClaims(String token) {
        JwtParser parser = Jwts.parser()  // Używamy parserBuilder() zamiast parser()
                .setSigningKey(secretKey)
                .build();  // Tworzymy parser

        Jws<Claims> claimsJws = parser.parseClaimsJws(token);  // Parsowanie tokenu JWT
        return claimsJws.getBody();  // Zwracanie zawartości (body) tokenu
    }

    // Pobieranie ID użytkownika z tokenu (zamiast nazwy)
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();  // Zwracamy userId (subject tokenu)
    }

    // Sprawdzenie, czy token wygasł
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Walidacja tokenu JWT
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()  // Użycie parserBuilder() weryfikujące klucz
                    .setSigningKey(secretKey)
                    .build();
            parser.parseClaimsJws(token);  // Sprawdzanie poprawności tokenu
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token compact of handler are invalid.");
        }
    }
}
