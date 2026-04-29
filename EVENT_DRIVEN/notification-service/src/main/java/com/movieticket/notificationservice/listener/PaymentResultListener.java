package com.movieticket.notificationservice.listener;

import com.movieticket.notificationservice.contract.BookingFailedEvent;
import com.movieticket.notificationservice.contract.PaymentCompletedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${app.rabbitmq.notification-queue}")
public class PaymentResultListener {

    @RabbitHandler
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        System.out.printf(
                "[NOTIFICATION] Booking #%s thanh cong! Transaction=%s, amount=%.1f%n",
                event.data().bookingId(),
                event.data().transactionId(),
                event.data().amount());
    }

    @RabbitHandler
    public void onBookingFailed(BookingFailedEvent event) {
        System.out.printf(
                "[NOTIFICATION] Booking #%s that bai! Ly do=%s%n",
                event.data().bookingId(),
                event.data().reason());
    }
}
