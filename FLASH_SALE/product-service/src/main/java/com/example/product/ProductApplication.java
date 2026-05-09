package com.example.product;

import org.springframework.boot.CommandLineRunner;
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
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApplication {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping
    public List<Product> getAllProducts() {
        try {
            Set<String> keys = redisTemplate.keys("product:*");
            if (keys == null) return Collections.emptyList();
            return keys.stream().map(key -> {
                try {
                    String val = redisTemplate.opsForValue().get(key);
                    return val != null ? objectMapper.readValue(val, Product.class) : null;
                } catch (Exception e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        try {
            String val = redisTemplate.opsForValue().get("product:" + id);
            return val != null ? objectMapper.readValue(val, Product.class) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {
            List<Product> products = Arrays.asList(
                new Product("1", "iPhone 15 Pro", 1200.0),
                new Product("2", "Samsung S24 Ultra", 1300.0),
                new Product("3", "MacBook M3", 2000.0)
            );
            for (Product p : products) {
                redisTemplate.opsForValue().set("product:" + p.getId(), objectMapper.writeValueAsString(p));
                if (redisTemplate.opsForValue().get("stock:" + p.getId()) == null) {
                    redisTemplate.opsForValue().set("stock:" + p.getId(), "100");
                }
            }
        };
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
class Product {
    private String id;
    private String name;
    private double price;
}
