package com.example.springauthservice.handler;

import com.example.springauthservice.model.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectURL;

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_" + Role.ADMIN.name()))) {
            redirectURL = "/admin";
        } else {
            redirectURL = "/user";
        }

        response.sendRedirect(redirectURL);
    }

}
