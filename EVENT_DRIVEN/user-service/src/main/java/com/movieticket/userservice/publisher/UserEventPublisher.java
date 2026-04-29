package com.movieticket.userservice.publisher;

import com.movieticket.userservice.contract.UserRegisteredEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String userRegisteredRoutingKey;

    public UserEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.exchange}") String exchange,
            @Value("${app.rabbitmq.user-registered-routing-key}") String userRegisteredRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.userRegisteredRoutingKey = userRegisteredRoutingKey;
    }

    public void publishUserRegistered(UserRegisteredEvent event) {
        rabbitTemplate.convertAndSend(exchange, userRegisteredRoutingKey, event);
    }
}
