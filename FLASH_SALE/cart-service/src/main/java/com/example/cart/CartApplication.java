package com.example.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartApplication {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    @PostMapping("/add")
    public void addToCart(@RequestParam String userId, @RequestBody CartItem item) {
        try {
            String key = "cart:" + userId;
            String currentCartJson = redisTemplate.opsForValue().get(key);
            List<CartItem> items;
            if (currentCartJson != null) {
                items = new ArrayList<>(Arrays.asList(objectMapper.readValue(currentCartJson, CartItem[].class)));
            } else {
                items = new ArrayList<>();
            }
            items.add(item);
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(items));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable String userId) {
        try {
            String val = redisTemplate.opsForValue().get("cart:" + userId);
            return val != null ? Arrays.asList(objectMapper.readValue(val, CartItem[].class)) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        redisTemplate.delete("cart:" + userId);
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

@Data @AllArgsConstructor @NoArgsConstructor
class CartItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
}
