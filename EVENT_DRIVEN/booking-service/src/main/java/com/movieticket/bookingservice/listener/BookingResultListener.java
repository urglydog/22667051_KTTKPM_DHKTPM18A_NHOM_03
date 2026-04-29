package com.movieticket.bookingservice.listener;

import com.movieticket.bookingservice.contract.BookingFailedEvent;
import com.movieticket.bookingservice.contract.PaymentCompletedEvent;
import com.movieticket.bookingservice.service.BookingService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${app.rabbitmq.booking-result-queue}")
public class BookingResultListener {

    private final BookingService bookingService;

    public BookingResultListener(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RabbitHandler
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        bookingService.markPaymentCompleted(event);
        System.out.printf("[BOOKING] Payment completed for booking %s.%n", event.data().bookingId());
    }

    @RabbitHandler
    public void onBookingFailed(BookingFailedEvent event) {
        bookingService.markBookingFailed(event);
        System.out.printf("[BOOKING] Booking %s failed because %s.%n", event.data().bookingId(), event.data().reason());
    }
}
