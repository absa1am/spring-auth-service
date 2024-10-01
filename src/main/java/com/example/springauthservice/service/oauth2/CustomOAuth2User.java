package com.example.springauthservice.service.oauth2;

import com.example.springauthservice.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User extends DefaultOAuth2User {

    private final User user;

    public CustomOAuth2User(User user, Map<String, Object> attributes) {
        super(Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())), attributes, "email");
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
