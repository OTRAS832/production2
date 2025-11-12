package com.OTRAS.DemoProject.Security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <-- Added HttpMethod import
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                // 🚀 CRITICAL FIX: Explicitly allow all OPTIONS pre-flight requests 
                // This ensures Spring Security does not intercept the CORS handshake.
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                
                // Allow specific auth endpoints
                .requestMatchers("/api/auth/**").permitAll() 
                
                // Allow everything else (if this is for a public API)
                .anyRequest().permitAll() 
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allowing all necessary frontend origins (including Vercel and localhost)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5171",
            "http://localhost:5172",
            "http://localhost:5173",
            "http://localhost:5174",
            "http://localhost:5175",
            "https://otras-admin-h5q6.vercel.app",
            "https://otrasuser.vercel.app",
            "https://otras-exam.vercel.app"
        ));

        // Ensure OPTIONS is explicitly listed here as well
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Allowing all headers
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        
        // Exposing headers
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Credentials must be true for cookies/auth headers
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
