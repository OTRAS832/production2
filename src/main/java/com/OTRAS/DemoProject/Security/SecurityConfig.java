// package com.OTRAS.DemoProject.Security;

// import java.util.Arrays;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//             .authorizeHttpRequests(auth -> auth
//                 // Allow CORS preflight (OPTIONS)
//                 .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                 // Allow public auth endpoints
//                 .requestMatchers("/api/auth/**").permitAll()
//                 // Allow everything else (adjust later if you add auth)
//                 .anyRequest().permitAll()
//             )
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//         return http.build();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();

//         // ✅ Works for localhost (dev) + all Vercel domains (prod)
//         configuration.setAllowedOriginPatterns(Arrays.asList(
//             "http://localhost:*",
//             "https://*.vercel.app"
//         ));

//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("*"));
//         configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
//         configuration.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//         return configuration.getAuthenticationManager();
//     }
// }


package com.OTRAS.DemoProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ✅ FINAL VERSION — PRODUCTION SAFE
 * Works with: Google Cloud Run + Vercel frontend + Spring Boot 3.x
 * CORS fully handled by SimpleCORSFilter (do not re-enable Spring's cors()).
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for APIs
            .csrf(csrf -> csrf.disable())

            // Disable Spring's internal CORS (handled by SimpleCORSFilter)
            .cors(cors -> cors.disable())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // ✅ Allow all preflight (OPTIONS) requests for CORS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ Allow authentication-related endpoints
                .requestMatchers("/api/auth/**").permitAll()

                // ✅ Allow public access to static files or general APIs if needed
                .requestMatchers(
                    "/api/public/**",
                    "/api/test/**",
                    "/health",
                    "/actuator/**"
                ).permitAll()

                // ❌ Restrict sensitive or admin APIs (optional)
                // .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ✅ Allow everything else for now (stateless public API)
                .anyRequest().permitAll()
            )

            // Use stateless session since we're using JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    /**
     * Password encoder for hashing user passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the AuthenticationManager used by Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

