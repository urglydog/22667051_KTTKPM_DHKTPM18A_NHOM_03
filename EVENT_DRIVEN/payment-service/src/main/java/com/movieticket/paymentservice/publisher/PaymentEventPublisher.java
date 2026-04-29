package com.movieticket.paymentservice.publisher;

import com.movieticket.paymentservice.contract.BookingFailedEvent;
import com.movieticket.paymentservice.contract.PaymentCompletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String paymentCompletedRoutingKey;
    private final String bookingFailedRoutingKey;

    public PaymentEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.exchange}") String exchange,
            @Value("${app.rabbitmq.payment-completed-routing-key}") String paymentCompletedRoutingKey,
            @Value("${app.rabbitmq.booking-failed-routing-key}") String bookingFailedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.paymentCompletedRoutingKey = paymentCompletedRoutingKey;
        this.bookingFailedRoutingKey = bookingFailedRoutingKey;
    }

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(exchange, paymentCompletedRoutingKey, event);
    }

    public void publishBookingFailed(BookingFailedEvent event) {
        rabbitTemplate.convertAndSend(exchange, bookingFailedRoutingKey, event);
    }
}
