package com.jobportal.backend.config;

import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.UserRepository;
import com.jobportal.backend.security.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter = new JwtFilter();

    // =========================
    // 🔥 FIX QUAN TRỌNG NHẤT
    // =========================
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username.trim())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority("ROLE_" + user.getRole())
                    )
            );
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth

            // 🌍 PUBLIC
            .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/categories/**").permitAll()

            // 👤 USER
            .requestMatchers("/applications/**").hasAnyRole("USER", "EMPLOYER")

            // 🏢 EMPLOYER
            .requestMatchers("/jobs/my").hasRole("EMPLOYER")
            .requestMatchers("/company/my").hasRole("EMPLOYER")

            .requestMatchers(HttpMethod.POST, "/jobs/**").hasRole("EMPLOYER")
            .requestMatchers(HttpMethod.PUT, "/jobs/**").hasRole("EMPLOYER")
            .requestMatchers(HttpMethod.DELETE, "/jobs/**").hasRole("EMPLOYER")

            .requestMatchers("/company/**").hasRole("EMPLOYER")

            // 👑 ADMIN
            .requestMatchers("/admin/**").hasRole("ADMIN")

            // 🌍 PUBLIC JOB VIEW (đặt SAU)
            .requestMatchers(HttpMethod.GET, "/jobs/**").permitAll()

            // 🔐 DEFAULT
            .anyRequest().authenticated()
        )
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
     @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}