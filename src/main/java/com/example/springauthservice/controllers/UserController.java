package com.example.springauthservice.controllers;

import com.example.springauthservice.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {
        var users = userService.getUsers(page, size);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("currentSize", size);

        return "dashboard/users";
    }

}
