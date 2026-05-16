package com.fooddelivery.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "ORDER_CREATED";

    @PostMapping
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> orderData) {
        // 1. Giả lập lưu vào Database
        String orderId = UUID.randomUUID().toString();
        orderData.put("id", orderId);
        orderData.put("status", "PENDING");
        orderData.put("createdAt", new Date());

        System.out.println("📦 Order saved: " + orderId);

        // 2. Publish Event lên Kafka cho Máy 4 xử lý
        kafkaTemplate.send(TOPIC, orderData);
        
        return Map.of(
            "message", "Order created successfully! Waiting for payment processing.",
            "orderId", orderId
        );
    }
}
