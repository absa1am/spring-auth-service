package com.example.springauthservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/user")
    public String userDashboard() {
        return "/dashboard/userDashboard";
    }

    @GetMapping("/admin")
    public String adminDashboard() {
        return "dashboard/adminDashboard";
    }

}
