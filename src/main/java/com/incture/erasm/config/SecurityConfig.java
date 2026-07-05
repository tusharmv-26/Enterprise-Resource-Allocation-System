package com.incture.erasm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.incture.erasm.security.CustomAccessDeniedHandler;
import com.incture.erasm.security.CustomAuthenticationEntryPoint;
import com.incture.erasm.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/users/**")
                    .hasRole("SUPER_ADMIN")
                .requestMatchers("/audit-logs/**")
                    .hasRole("SUPER_ADMIN")
                .requestMatchers("/roles/**")
                    .hasRole("SUPER_ADMIN")
                .requestMatchers("/employees/**")
                    .hasAnyRole("SUPER_ADMIN", "RESOURCE_MANAGER")
                .requestMatchers("/projects/**")
                    .hasAnyRole("SUPER_ADMIN", "PROJECT_MANAGER")
                .requestMatchers("/resource-requests/**")
                    .hasAnyRole("PROJECT_MANAGER", "RESOURCE_MANAGER")
                .requestMatchers("/allocations/**")
                    .hasRole("RESOURCE_MANAGER")
                .requestMatchers("/reports/**")
                    .hasAnyRole("SUPER_ADMIN", "RESOURCE_MANAGER")
                .requestMatchers("/dashboard/**")
                    .authenticated()
                .requestMatchers("/skills/**")
                    .authenticated()
                .requestMatchers("/certifications/**")
                    .authenticated()
                .requestMatchers("/employee-skill-levels/**")
                    .authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}