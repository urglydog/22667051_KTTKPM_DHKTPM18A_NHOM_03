package com.movieticket.paymentservice.service;

import com.movieticket.paymentservice.model.PaymentStatus;
import com.movieticket.paymentservice.model.PaymentTransaction;
import com.movieticket.paymentservice.contract.BookingCreatedData;
import com.movieticket.paymentservice.contract.BookingCreatedEvent;
import com.movieticket.paymentservice.contract.BookingFailedData;
import com.movieticket.paymentservice.contract.BookingFailedEvent;
import com.movieticket.paymentservice.contract.PaymentCompletedData;
import com.movieticket.paymentservice.contract.PaymentCompletedEvent;
import com.movieticket.paymentservice.publisher.PaymentEventPublisher;
import com.movieticket.paymentservice.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentProcessingService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    public PaymentProcessingService(
            PaymentTransactionRepository paymentTransactionRepository,
            PaymentEventPublisher paymentEventPublisher) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    public void processBookingCreated(BookingCreatedEvent event) {
        validateEvent(event);
        BookingCreatedData booking = event.data();

        boolean success = ThreadLocalRandom.current().nextInt(100) < 70;
        PaymentStatus paymentStatus = success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        PaymentTransaction tx = new PaymentTransaction();
        tx.setBookingId(booking.bookingId());
        tx.setAmount(BigDecimal.valueOf(booking.amount()));
        tx.setStatus(paymentStatus);
        tx.setProcessedAt(Instant.now());
        paymentTransactionRepository.save(tx);

        if (success) {
            paymentEventPublisher.publishPaymentCompleted(
                    new PaymentCompletedEvent(
                            "EVT-P-" + UUID.randomUUID(),
                            "PAYMENT_COMPLETED",
                            Instant.now().toString(),
                            new PaymentCompletedData(
                                    booking.bookingId(),
                                    "TXN-" + UUID.randomUUID(),
                                    "MB eBanking",
                                    booking.amount(),
                                    "SUCCESS")));
            System.out.printf("[PAYMENT] Booking %s paid successfully.%n", booking.bookingId());
            return;
        }

        paymentEventPublisher.publishBookingFailed(
                new BookingFailedEvent(
                        "EVT-P-" + UUID.randomUUID(),
                        "BOOKING_FAILED",
                        Instant.now().toString(),
                        new BookingFailedData(
                                booking.bookingId(),
                                "Payment timeout or Insufficient balance",
                                "FAILED")));
        System.out.printf("[PAYMENT] Booking %s failed to pay.%n", booking.bookingId());
    }

    private void validateEvent(BookingCreatedEvent event) {
        if (event == null || event.data() == null) {
            throw new IllegalArgumentException("Invalid BOOKING_CREATED event: payload is empty");
        }
        if (event.data().bookingId() == null || event.data().bookingId().isBlank()) {
            throw new IllegalArgumentException("Invalid BOOKING_CREATED event: bookingId is required");
        }
        if (event.data().amount() == null || event.data().amount() <= 0) {
            throw new IllegalArgumentException("Invalid BOOKING_CREATED event: amount must be greater than 0");
        }
    }
}
