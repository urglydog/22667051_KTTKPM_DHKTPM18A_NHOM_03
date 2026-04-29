package com.movieticket.paymentservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.payment-queue}")
    private String paymentQueue;

    @Value("${app.rabbitmq.booking-created-routing-key}")
    private String bookingCreatedRoutingKey;

    @Value("${app.rabbitmq.dlq.exchange}")
    private String dlx;

    @Value("${app.rabbitmq.dlq.queue}")
    private String dlqQueue;

    @Value("${app.rabbitmq.dlq.routing-key}")
    private String dlqRoutingKey;

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlx, true, false);
    }

    @Bean
    public Queue paymentQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", dlx);
        args.put("x-dead-letter-routing-key", dlqRoutingKey);
        return new Queue(paymentQueue, true, false, false, args);
    }

    @Bean
    public Queue bookingEventsDlq() {
        return new Queue(dlqQueue, true);
    }

    @Bean
    public Binding paymentQueueBinding(Queue paymentQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(paymentQueue).to(bookingExchange).with(bookingCreatedRoutingKey);
    }

    @Bean
    public Binding dlqBinding(Queue bookingEventsDlq, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(bookingEventsDlq).to(deadLetterExchange).with(dlqRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        typeMapper.setIdClassMapping(Map.of(
                "BOOKING_CREATED", com.movieticket.paymentservice.contract.BookingCreatedEvent.class,
                "PAYMENT_COMPLETED", com.movieticket.paymentservice.contract.PaymentCompletedEvent.class,
                "BOOKING_FAILED", com.movieticket.paymentservice.contract.BookingFailedEvent.class));
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setDefaultRequeueRejected(false);
        factory.setAdviceChain(
                RetryInterceptorBuilder
                        .stateless()
                        .maxAttempts(3)
                        .backOffOptions(2000, 1.0, 2000)
                        .recoverer(new org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer())
                        .build());
        factory.setErrorHandler(new ConditionalRejectingErrorHandler());
        return factory;
    }
}
