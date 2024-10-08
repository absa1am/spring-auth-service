package com.example.springauthservice.service.email;

import com.example.springauthservice.model.EmailConfirmationToken;
import com.example.springauthservice.model.User;
import com.example.springauthservice.repository.EmailConfirmationTokenRepository;
import com.example.springauthservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private final UserRepository userRepository;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    public EmailVerificationService(UserRepository userRepository, EmailConfirmationTokenRepository emailConfirmationTokenRepository) {
        this.userRepository = userRepository;
        this.emailConfirmationTokenRepository = emailConfirmationTokenRepository;
    }

    public boolean verifyEmail(String token) {
        User user = null;
        EmailConfirmationToken emailConfirmationToken = emailConfirmationTokenRepository.findByToken(token);

        if (emailConfirmationToken != null) {
            user = userRepository.findById(emailConfirmationToken.getUser().getId()).orElse(null);
        }

        if (user != null) {
            user.setEnabled(true);

            userRepository.save(user);

            return true;
        }

        return false;
    }

}
