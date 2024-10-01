package com.example.springauthservice.controller;

import com.example.springauthservice.helper.Message;
import com.example.springauthservice.helper.enums.MessageType;
import com.example.springauthservice.model.User;
import com.example.springauthservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(@RequestParam("confirmPassword") String confirmPassword, @Valid @ModelAttribute("user") User user, BindingResult errors, RedirectAttributes redirectAttributes) {
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

        redirectAttributes.addFlashAttribute("message", new Message("Registration is successful, login to continue.", MessageType.SUCCESS));

        return "redirect:/login";
    }

}
