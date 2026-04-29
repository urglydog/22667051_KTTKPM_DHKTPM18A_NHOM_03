package com.movieticket.userservice.controller;

import com.movieticket.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserService.RegisterRequest request) {
        return ResponseEntity.ok(Map.of(
                "message", "Register successful",
                "data", userService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserService.LoginRequest request) {
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "data", userService.login(request)));
    }
}
