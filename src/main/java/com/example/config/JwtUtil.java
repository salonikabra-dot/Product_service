package com.example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey123";

    private Key getSigningKey() {
        System.out.println("[JwtUtil] Using secret: " + SECRET);

        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getAllClaims(token);
            Date expiration = claims.getExpiration();
            Date now = new Date();

            System.out.println("[JwtUtil] Token expiration: " + expiration + ", Current time: " + now);

            boolean valid = expiration == null || expiration.after(now);
            if (!valid) {
                System.out.println("[JwtUtil] Token is expired!");
            }
            return valid;
        } catch (Exception e) {
            System.out.println("[JwtUtil] Token validation failed: " + e.getMessage());
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
