package com.jobportal.backend.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    // 🔥 BỎ QUA AUTH
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("👉 REQUEST PATH: " + path);

        String header = request.getHeader("Authorization");
        System.out.println("👉 HEADER: " + header);

        // ❌ Không có token
        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("❌ NO TOKEN");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        System.out.println("🔥 TOKEN: " + token);

        String email = JwtUtil.validateToken(token);
        String role = JwtUtil.getRoleFromToken(token);

        System.out.println("🔥 EMAIL: " + email);
        System.out.println("🔥 ROLE: " + role);

        // ❌ Token lỗi
        if (email == null || role == null) {
            System.out.println("❌ TOKEN INVALID");
            filterChain.doFilter(request, response);
            return;
        }

        // 🔥 ROLE_
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        System.out.println("✅ FINAL ROLE: " + role);

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        authorities
                );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);

        System.out.println("✅ AUTH SET SUCCESS");

        filterChain.doFilter(request, response);
    }
}