package com.capstonetwo.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for development
            .authorizeHttpRequests(auth -> auth
                // ALLOW USER and ADMIN to GET (view/search)
                .requestMatchers(HttpMethod.GET, "/api/employees/**", "/api/departments/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/employee/**", "/department/**").hasAnyRole("USER", "ADMIN")

                // Only ADMIN can POST, PUT, DELETE (modify)
                .requestMatchers(HttpMethod.POST, "/api/employees/**", "/api/departments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/employees/**", "/api/departments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**", "/api/departments/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/employee/**", "/department/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/employee/*/edit", "/department/*/edit").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/employee/*/delete", "/department/*/delete").hasRole("ADMIN")

                // Allow everything else
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
            	.loginPage("/login") //custom login view at /login
            	.loginProcessingUrl("/login") 
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Redirect to login with logout message
                .permitAll()
            );

        return http.build();
    }
}
