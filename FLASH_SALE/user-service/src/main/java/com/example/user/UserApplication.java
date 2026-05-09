package com.example.user;

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

@SpringBootApplication
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApplication {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        try {
            String val = redisTemplate.opsForValue().get("user:" + id);
            return val != null ? objectMapper.readValue(val, User.class) : null;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        try {
            redisTemplate.opsForValue().set("user:" + user.getId(), objectMapper.writeValueAsString(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    CommandLineRunner initUsers() {
        return args -> {
            List<User> users = Arrays.asList(
                new User("user_1", "Nguyen Van A", "Silver"),
                new User("user_2", "Tran Thi B", "Gold"),
                new User("user_3", "Le Van C", "Platinum")
            );
            for (User u : users) {
                redisTemplate.opsForValue().set("user:" + u.getId(), objectMapper.writeValueAsString(u));
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
class User {
    private String id;
    private String name;
    private String rank;
}
