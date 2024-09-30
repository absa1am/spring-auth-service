package com.example.springauthservice.config;

import com.example.springauthservice.handler.OAuth2Handler;
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

    private final AuthHandler authHandler;
    private final OAuth2Handler oAuth2Handler;

    public SecurityConfig(AuthHandler authHandler, OAuth2Handler oAuth2Handler) {
        this.authHandler = authHandler;
        this.oAuth2Handler = oAuth2Handler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.formLogin(form ->
                {
                    form.loginPage("/login")
                            .permitAll()
                            .successHandler(authHandler);
                })
                .oauth2Login(oAuth2LoginConfigurer ->
                {
                    oAuth2LoginConfigurer.loginPage("/login")
                            .successHandler(oAuth2Handler);
                })
                .authorizeHttpRequests(authRegistry ->
                {
                    authRegistry.requestMatchers("/", "/register", "/public/**")
                            .permitAll()
                            .requestMatchers("/user/**").hasAnyAuthority("ROLE_" + Role.USER.name(), "OAUTH2_" + Role.USER.name())
                            .requestMatchers("/admin/**").hasAnyAuthority("ROLE_" + Role.ADMIN.name())
                            .anyRequest()
                            .authenticated();
                })
                .logout(logout -> logout.logoutSuccessUrl("/login"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
