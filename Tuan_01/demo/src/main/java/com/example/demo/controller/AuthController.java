package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public String login(Authentication authentication) {
        // Hàm này sẽ được Spring Security tự động cung cấp đối tượng authentication
        // sau khi xác thực Basic Auth thành công
        return tokenService.generateToken(authentication);
    }
    @GetMapping("/api/profile")
    public String getProfile(Authentication authentication) {
        // API này sẽ trả về tên người dùng được trích xuất từ Token
        return "Chào mừng " + authentication.getName() + "! Bạn đã truy cập API bằng Token thành công.";
    }
}
