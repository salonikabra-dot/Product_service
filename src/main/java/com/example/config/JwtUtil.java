package com.example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey123"; // same as UserService

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public boolean isTokenValid(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractTenantId(String token) {
        return getAllClaims(token).get("tenantId", String.class);
    }

    public String extractUserId(String token) {
        return getAllClaims(token).get("userId", String.class);
    }

    public String extractRole(String token) {
        return getAllClaims(token).get("role", String.class);
    }

    public String extractEmail(String token) {
        return getAllClaims(token).getSubject();
    }
}
