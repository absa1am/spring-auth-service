package com.example.springauthservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "/dashboard/userDashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "dashboard/adminDashboard";
    }

}
