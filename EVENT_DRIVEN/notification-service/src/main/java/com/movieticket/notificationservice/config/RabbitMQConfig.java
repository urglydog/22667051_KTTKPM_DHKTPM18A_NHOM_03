package com.movieticket.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.notification-queue}")
    private String notificationQueue;

    @Value("${app.rabbitmq.payment-completed-routing-key}")
    private String paymentCompletedRoutingKey;

    @Value("${app.rabbitmq.booking-failed-routing-key}")
    private String bookingFailedRoutingKey;

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    @Bean
    public Binding paymentCompletedBinding(Queue notificationQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(notificationQueue).to(bookingExchange).with(paymentCompletedRoutingKey);
    }

    @Bean
    public Binding bookingFailedBinding(Queue notificationQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(notificationQueue).to(bookingExchange).with(bookingFailedRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        typeMapper.setIdClassMapping(Map.of(
                "PAYMENT_COMPLETED", com.movieticket.notificationservice.contract.PaymentCompletedEvent.class,
                "BOOKING_FAILED", com.movieticket.notificationservice.contract.BookingFailedEvent.class));
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
