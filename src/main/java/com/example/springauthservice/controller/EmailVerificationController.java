package com.example.springauthservice.controller;

import com.example.springauthservice.service.email.EmailVerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("/verify-email")
    public String verifyEmail() {
        return "/email/verify-email";
    }

    @PostMapping("/verify-email/{token}")
    public String verifyEmail(@PathVariable String token) {
        emailVerificationService.verifyEmail(token);

        return "redirect:/email/verified-email";
    }

}
