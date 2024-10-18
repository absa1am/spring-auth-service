package com.example.springauthservice.service.oauth2;

import com.example.springauthservice.model.User;
import com.example.springauthservice.model.enums.AuthType;
import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = userService.loadUser(userRequest);
        String authorizedClientRegistrationId = userRequest.getClientRegistration().getRegistrationId();

        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();

            newUser.setName(name);
            newUser.setUsername(email);
            newUser.setEmail(email);
            newUser.setRole(Role.USER);
            newUser.setEnabled(true);
            newUser.setActive(false);
            newUser.setDeleted(false);
            newUser.setModifiedDate(LocalDate.now());
            newUser.setCreatedDate(LocalDate.now());

            return newUser;
        });

        user.setModifiedDate(LocalDate.now());

        if (authorizedClientRegistrationId.equals("google")) {
            user.setAuthType(AuthType.GOOGLE);
        } else if (authorizedClientRegistrationId.equals("github")) {
            user.setAuthType(AuthType.GITHUB);
        }

        userRepository.save(user);

        return new CustomOAuth2User(user, oAuth2User.getAttributes());
    }

}
