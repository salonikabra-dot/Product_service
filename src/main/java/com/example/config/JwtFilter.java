package com.example.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean skip = path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs");
        if (skip) {
            System.out.println("[JwtFilter] Skipping filter for path: " + path);
        }
        return skip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("[JwtFilter] Incoming request: " + method + " " + path);

        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            System.out.println("[JwtFilter] Authorization header: " + authHeader);
        } else {
            System.out.println("[JwtFilter] No Authorization header found");
        }

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);
                System.out.println("[JwtFilter] Extracted token: " + token);

                if (jwtUtil.isTokenValid(token)) {
                    String email = jwtUtil.extractEmail(token);
                    String role = jwtUtil.extractRole(token);
                    System.out.println("[JwtFilter] Token valid. Email: " + email + ", Role: " + role);

                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("[JwtFilter] Authentication set in SecurityContext for: " + email);
                } else {
                    System.out.println("[JwtFilter] Token is invalid or expired!");
                }
            } else {
                System.out.println("[JwtFilter] No Bearer token found in header.");
            }
        } catch (Exception e) {
            System.out.println("[JwtFilter] Exception during JWT processing: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
