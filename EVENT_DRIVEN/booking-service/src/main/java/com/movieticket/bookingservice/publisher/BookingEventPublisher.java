package com.movieticket.bookingservice.publisher;

import com.movieticket.bookingservice.contract.BookingCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BookingEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String bookingCreatedRoutingKey;

    public BookingEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.exchange}") String exchange,
            @Value("${app.rabbitmq.booking-created-routing-key}") String bookingCreatedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.bookingCreatedRoutingKey = bookingCreatedRoutingKey;
    }

    public void publishBookingCreated(BookingCreatedEvent event) {
        rabbitTemplate.convertAndSend(exchange, bookingCreatedRoutingKey, event);
    }
}
