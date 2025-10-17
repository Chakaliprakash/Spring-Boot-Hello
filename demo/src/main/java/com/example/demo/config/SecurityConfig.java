package com.example.demo.config;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.User; // Your JPA entity
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UserDetailsService to load user credentials from DB
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User userEntity = userRepository.findByUsername(username);
            if (userEntity == null) {
                throw new UsernameNotFoundException("User not found");
            }

            // Fully qualified Spring Security User to avoid ambiguity
            return org.springframework.security.core.userdetails.User
                    .withUsername(userEntity.getUsername())
                    .password(userEntity.getPassword()) // Already encoded in DB
                    .roles("USER")
                    .build();
        };
    }

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/h2-console/**").permitAll()
                        .anyRequest().authenticated())
            .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/hello", true)
                        .permitAll())
            .logout(logout -> logout.permitAll());

        http.csrf(csrf -> csrf.disable()); // Required for H2 console
        http.headers(headers -> headers.frameOptions().disable()); // Required for H2 console

        return http.build();
    }
}
