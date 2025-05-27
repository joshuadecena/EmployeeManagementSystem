package com.capstonetwo.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // maps to login.html
    }

    @GetMapping("/auth/home")  // Changed to avoid conflict
    public String home() {
        return "home"; // or you can have a separate authHome.html if needed
    }
}
