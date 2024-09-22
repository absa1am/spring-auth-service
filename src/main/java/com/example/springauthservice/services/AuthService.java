package com.example.springauthservice.services;

import com.example.springauthservice.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class AuthService implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectURL;

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals(Role.ADMIN.name()))) {
            redirectURL = "/admin";
        } else {
            redirectURL = "/user";
        }

        response.sendRedirect(redirectURL);
    }

}
