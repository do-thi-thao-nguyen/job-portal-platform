    package com.jobportal.backend.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


    import org.springframework.http.HttpMethod;

    import com.jobportal.backend.security.JwtFilter;

    @Configuration
    @EnableMethodSecurity
    public class SecurityConfig {

        // ✅ JWT FILTER
        @Bean
        public JwtFilter jwtFilter() {
            return new JwtFilter();
        }

        // ✅ PASSWORD ENCODER
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        // ✅ SECURITY
        @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.addAllowedOrigin("http://localhost:3000");
                corsConfig.addAllowedHeader("*");
                corsConfig.addAllowedMethod("*");
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))

            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .authorizeHttpRequests(auth -> auth

                // 🔥 QUAN TRỌNG
                .requestMatchers("/auth/**").permitAll()

                .requestMatchers("/momo/**").hasRole("EMPLOYER")

                .requestMatchers("/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/jobs/**").permitAll()

                .requestMatchers("/applications/**").hasAnyRole("USER", "EMPLOYER")

                .requestMatchers("/jobs/my").hasRole("EMPLOYER")
                .requestMatchers("/company/**").hasRole("EMPLOYER")

                .requestMatchers(HttpMethod.POST, "/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.PUT, "/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.DELETE, "/jobs/**").hasRole("EMPLOYER")

                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}