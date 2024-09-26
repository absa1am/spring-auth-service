package com.example.springauthservice.config;

import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.formLogin(form ->
                {
                    form.loginPage("/login")
                            .permitAll()
                            .successHandler(new AuthHandler());
                })
                .authorizeHttpRequests(authRegistry ->
                {
                    authRegistry.requestMatchers("/", "/register", "/public/**")
                            .permitAll()
                            .requestMatchers("/user/**").hasAuthority(Role.USER.name())
                            .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                            .anyRequest()
                            .authenticated();
                })
                .logout(logout -> logout.logoutSuccessUrl("/login"))
                .build();
    }

}
