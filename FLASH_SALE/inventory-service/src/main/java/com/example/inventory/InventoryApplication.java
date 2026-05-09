package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class InventoryApplication {

    private final StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @GetMapping("/{productId}")
    public Integer getStock(@PathVariable String productId) {
        String stock = redisTemplate.opsForValue().get("stock:" + productId);
        return stock != null ? Integer.parseInt(stock) : 0;
    }

    @PostMapping("/deduct")
    public boolean deductStock(@RequestParam String productId, @RequestParam int quantity) {
        Long remaining = redisTemplate.opsForValue().decrement("stock:" + productId, quantity);
        if (remaining != null && remaining >= 0) {
            return true;
        } else {
            redisTemplate.opsForValue().increment("stock:" + productId, quantity);
            return false;
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
