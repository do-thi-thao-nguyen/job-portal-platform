package com.jobportal.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private static final Key SECRET_KEY =
            Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes());

    // ================= VALIDATE =================
    public static String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // email
        } catch (Exception e) {
            return null;
        }
    }

    // ================= GET ROLE =================
    public static String getRoleFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    // ================= GENERATE =================
    public static String generateToken(String email, String role) {

        if (role == null) {
            throw new RuntimeException("Role không được null");
        }

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.replace("ROLE_", "")) // vẫn phải có claim
                .signWith(SECRET_KEY)
                .compact();
    }
}