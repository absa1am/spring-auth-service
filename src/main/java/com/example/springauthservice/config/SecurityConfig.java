package com.example.springauthservice.config;

import com.example.springauthservice.handler.CustomAuthenticationSuccessHandler;
import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.repository.UserRepository;
import com.example.springauthservice.service.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults());

        http.authorizeHttpRequests(requests ->
        {
            requests.requestMatchers("/", "/login", "/register", "/public/**").permitAll()
                    .requestMatchers("/admin/dashboard/**").hasRole(Role.ADMIN.name())
                    .requestMatchers("/user/dashboard/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .anyRequest().authenticated();
        });

        http.formLogin(auth ->
        {
            auth.loginPage("/login")
                    .successHandler(successHandler());
        });

        http.oauth2Login(oauth ->
        {
            oauth.loginPage("/login")
                    .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService()))
                    .successHandler(successHandler());
        });

        http.logout(logout ->
        {
            logout.logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/");
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new CustomOAuth2UserService(userRepository);
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
