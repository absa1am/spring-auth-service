package com.example.springauthservice.config;

import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.handler.AuthHandler;
import com.example.springauthservice.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
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
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }

}
