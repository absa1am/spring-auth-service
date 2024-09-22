package com.example.springauthservice.controllers;

import com.example.springauthservice.models.User;
import com.example.springauthservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());

        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("confirmPassword") String confirmPassword, @Valid @ModelAttribute("user") User user, BindingResult errors) {
        if (!confirmPassword.equals(user.getPassword())) {
            errors.rejectValue("password", "error.password", "Password is not matched.");
        }

        if (errors.hasErrors()) {
            return "auth/register";
        }

        try {
            User savedUser =  userService.saveUser(user);
        } catch (DataIntegrityViolationException exception) {
            errors.rejectValue("username", "error.username", "The username or email already exists.");

            return "auth/register";
        }

        return "redirect:/login";
    }

}
