package com.ridenshine.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Value("${app.admin.username}") private String adminUsername;
    @Value("${app.admin.password}") private String adminPassword;
    @Value("${app.cors.allowed-origins:http://localhost:5173}") private String allowedOrigins;

    @Bean PasswordEncoder passwordEncoder() { return PasswordEncoderFactories.createDelegatingPasswordEncoder(); }
    @Bean UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername(adminUsername).password(encoder.encode(adminPassword)).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(admin);
    }
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health", "/actuator/info", "/", "/index.html", "/assets/**", "/logo.png", "/banner.png", "/product-placeholder.svg", "/products", "/services", "/booking", "/reviews", "/admin").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/services/**", "/api/reviews/**", "/api/offers/**", "/api/bookings/reference/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/bookings", "/api/reviews").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().denyAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
