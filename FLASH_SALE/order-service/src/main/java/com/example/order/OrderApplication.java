package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;
import lombok.*;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApplication {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${service.cart.url}")
    private String cartServiceUrl;

    @Value("${service.inventory.url}")
    private String inventoryServiceUrl;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @PostMapping("/checkout")
    public Map<String, Object> checkout(@RequestParam String userId) {
        Map<String, Object> response = new HashMap<>();
        CartItem[] cartItems = restTemplate.getForObject(cartServiceUrl + "/api/cart/" + userId, CartItem[].class);
        if (cartItems == null || cartItems.length == 0) {
            response.put("success", false);
            response.put("message", "Cart is empty");
            return response;
        }

        for (CartItem item : cartItems) {
            String url = inventoryServiceUrl + "/api/stock/deduct?productId=" + item.getProductId() + "&quantity=" + item.getQuantity();
            Boolean success = restTemplate.postForObject(url, null, Boolean.class);
            if (success == null || !success) {
                response.put("success", false);
                response.put("message", "Out of stock for product: " + item.getProductName());
                return response;
            }
        }

        restTemplate.delete(cartServiceUrl + "/api/cart/" + userId);
        response.put("success", true);
        response.put("orderId", UUID.randomUUID().toString());
        response.put("message", "Order placed successfully!");
        return response;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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
