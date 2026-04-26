package com.jobportal.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 🔥 Nếu không có token → bỏ qua
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        Claims claims = JwtUtil.validateToken(token);

        // 🔥 Token invalid
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        // 🔥 Nếu không có role → bỏ qua (tránh ROLE_null)
        if (role == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔥 đảm bảo luôn có ROLE_
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(role)
        );

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        authorities
                );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}