package com.example.springauthservice.repository;

import com.example.springauthservice.model.EmailConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {

    EmailConfirmationToken findByToken(String token);
    EmailConfirmationToken findByUserId(Long userId);

}
