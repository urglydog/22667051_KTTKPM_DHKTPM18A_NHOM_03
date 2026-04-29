package com.movieticket.bookingservice.contract;

public record PaymentCompletedEvent(
        String eventId,
        String eventType,
        String timestamp,
        PaymentCompletedData data) {
}
