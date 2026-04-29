package com.movieticket.paymentservice.contract;

public record PaymentCompletedEvent(
        String eventId,
        String eventType,
        String timestamp,
        PaymentCompletedData data) {
}
