package com.jobportal.backend.config;

import com.jobportal.backend.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter = new JwtFilter();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth

                // ========================
                // 🌍 PUBLIC
                // ========================
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/jobs/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ========================
                // 👤 USER (ỨNG VIÊN)
                // ========================
                .requestMatchers("/applications/**").hasRole("USER")

                // ========================
                // 🏢 EMPLOYER
                // ========================
                // 🔥 FIX: gom lại cho gọn + tránh miss rule
                .requestMatchers(HttpMethod.POST, "/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.PUT, "/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.DELETE, "/jobs/**").hasRole("EMPLOYER")

                // 🔥 FIX: tách rõ company API
                .requestMatchers("/company/**").hasRole("EMPLOYER")

                // ========================
                // 👑 ADMIN
                // ========================
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // ========================
                // 🔐 DEFAULT
                // ========================
                .anyRequest().authenticated()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}