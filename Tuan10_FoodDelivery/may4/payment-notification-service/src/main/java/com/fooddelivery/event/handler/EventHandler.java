package com.fooddelivery.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EventHandler {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // 1. Lắng nghe đơn hàng mới từ Máy 3
    @KafkaListener(topics = "ORDER_CREATED", groupId = "group_payment")
    public void handleOrderCreated(Map<String, Object> orderData) {
        String orderId = (String) orderData.get("id");
        System.out.println("💸 Đang xử lý thanh toán cho đơn: " + orderId);

        // Random thành công hoặc thất bại
        boolean isSuccess = new Random().nextBoolean();
        
        if (isSuccess) {
            kafkaTemplate.send("PAYMENT_SUCCESS", orderId);
        } else {
            kafkaTemplate.send("PAYMENT_FAILED", orderId);
        }
    }

    // 2. Lắng nghe kết quả thanh toán để gửi thông báo
    @KafkaListener(topics = "PAYMENT_SUCCESS", groupId = "group_notification")
    public void handlePaymentSuccess(String orderId) {
        System.out.println("🔔 THÔNG BÁO: Đơn hàng #" + orderId + " đã thanh toán thành công! Nhà hàng đang chuẩn bị món.");
    }

    @KafkaListener(topics = "PAYMENT_FAILED", groupId = "group_notification")
    public void handlePaymentFailed(String orderId) {
        System.out.println("❌ THÔNG BÁO: Thanh toán thất bại cho đơn hàng #" + orderId + ". Vui lòng thử lại.");
    }
}
