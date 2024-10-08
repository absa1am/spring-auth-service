package com.example.springauthservice.service;

import com.example.springauthservice.model.EmailConfirmationToken;
import com.example.springauthservice.model.User;
import com.example.springauthservice.model.enums.AuthType;
import com.example.springauthservice.model.enums.Role;
import com.example.springauthservice.repository.EmailConfirmationTokenRepository;
import com.example.springauthservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailConfirmationTokenRepository emailConfirmationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailConfirmationTokenRepository = emailConfirmationTokenRepository;
    }

    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setAuthType(AuthType.LOCAL);
        user.setCreatedDate(LocalDate.now());
        user.setModifiedDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        var emailConfirmationToken = new EmailConfirmationToken(UUID.randomUUID().toString(), LocalDate.now(), savedUser);

        emailConfirmationTokenRepository.save(emailConfirmationToken);

        return savedUser;
    }

    public Page<User> getUsers(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return userRepository.findAll(PageRequest.of(page, size));
    }

}
