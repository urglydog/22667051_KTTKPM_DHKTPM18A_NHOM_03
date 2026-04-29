package com.movieticket.paymentservice.listener;

import com.movieticket.paymentservice.contract.BookingCreatedEvent;
import com.movieticket.paymentservice.service.PaymentProcessingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedListener {

    private final PaymentProcessingService paymentProcessingService;

    public BookingCreatedListener(PaymentProcessingService paymentProcessingService) {
        this.paymentProcessingService = paymentProcessingService;
    }

    @RabbitListener(queues = "${app.rabbitmq.payment-queue}")
    public void handleBookingCreated(BookingCreatedEvent event) {
        System.out.printf("[PAYMENT] Received event %s for booking %s.%n", event.eventType(), event.data().bookingId());
        paymentProcessingService.processBookingCreated(event);
    }
}
