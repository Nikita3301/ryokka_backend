package com.sora.ryokka.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Enable CORS configuration
        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.addAllowedOrigin("http://localhost:5173");  // React frontend URL
            corsConfig.addAllowedMethod("*");  // Allow all HTTP methods
            corsConfig.addAllowedHeader("*");  // Allow all headers
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }));

        // Disable CSRF (for stateless APIs)
        http.csrf(AbstractHttpConfigurer::disable);


        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/projects/1/upload","/api/projects", "/api/projects/{id}", "/api/employees", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Allow public access
                                .anyRequest().permitAll() // Require authentication for other requests
                );

//        // Add your Firebase authentication filter
//        http.addFilterAfter(new FirebaseAuthenticationFilter(), BasicAuthenticationFilter.class)
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/api/projects").permitAll()
//                                .requestMatchers("/api/employees", "/api/projects").permitAll()
//                                .anyRequest().authenticated()
////                                .requestMatchers(WebConstants.API_BASE_PATH).authenticated()  // Secure API
//                );

        return http.build();
    }

}
