package com.mundke.quizoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/", "/index", "/login", "/register", "/error").permitAll()
                .requestMatchers("/WEB-INF/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/quizzes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/quizzes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/quizzes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/quizzes/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/results/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/quizzes/**", "/quiz/**", "/submit", "/results").hasRole("USER")
                .requestMatchers("/home").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}