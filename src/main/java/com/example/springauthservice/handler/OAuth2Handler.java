package com.example.springauthservice.handler;

import com.example.springauthservice.model.User;
import com.example.springauthservice.model.enums.AuthType;
import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class OAuth2Handler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2Handler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        User user = new User();

        user.setName(oAuth2User.getAttribute("name"));
        user.setUsername(oAuth2User.getAttribute("email"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setRole(Role.USER);
        user.setActive(false);
        user.setDeleted(false);
        user.setModifiedDate(LocalDate.now());
        user.setCreatedDate(LocalDate.now());

        if (authorizedClientRegistrationId.equals("google")) {
            user.setAuthType(AuthType.GOOGLE);
        } else if (authorizedClientRegistrationId.equals("github")) {
            user.setAuthType(AuthType.GITHUB);
        }

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isEmpty()) {
            userRepository.save(user);
        }

        String redirectURL;

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("OAUTH2_" + Role.ADMIN.name()))) {
            redirectURL = "/admin";
        } else {
            redirectURL = "/user";
        }

        response.sendRedirect(redirectURL);
    }

}
