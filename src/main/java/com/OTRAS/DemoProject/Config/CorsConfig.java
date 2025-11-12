package com.OTRAS.DemoProject.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // .allowedOrigins(
                        //         "http://localhost:5171",
                        //         "http://localhost:5172",
                        //         "http://localhost:5173",
                        //         "http://localhost:5174",
                        //         "http://localhost:5175",
                        //         "https://otras-admin-h5q6.vercel.app",
                        //         "https://otrasuser.vercel.app",
                        //         "https://otras-exam.vercel.app"
                        // )
                      .allowedOriginPatterns(
                                "http://localhost:*",
                                "https://*.vercel.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
