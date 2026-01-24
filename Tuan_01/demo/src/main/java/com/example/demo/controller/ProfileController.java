package com.example.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // QUAN TRỌNG: Phải có dòng này để hết lỗi 404
public class ProfileController {

    @GetMapping("/api/profile")
    public String getProfile(Authentication authentication) {
        return "Chào mừng " + authentication.getName() + "! Token RSA của bạn hoạt động rất tốt.";
    }
}
