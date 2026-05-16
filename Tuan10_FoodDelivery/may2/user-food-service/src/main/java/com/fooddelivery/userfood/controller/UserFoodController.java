package com.fooddelivery.userfood.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserFoodController {

    // Giả lập danh sách món ăn
    @GetMapping("/foods")
    public List<Map<String, Object>> getFoods() {
        return Arrays.asList(
            Map.of("id", 1, "name", "Phở Bò", "price", 50000),
            Map.of("id", 2, "name", "Bún Chả", "price", 45000),
            Map.of("id", 3, "name", "Cơm Tấm", "price", 35000)
        );
    }

    // Giả lập đăng nhập
    @PostMapping("/users/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        if ("admin".equals(credentials.get("username"))) {
            return Map.of("status", "success", "token", "fake-jwt-token");
        }
        return Map.of("status", "fail", "message", "Invalid credentials");
    }
}
