package com.movieticket.notificationservice.contract;

public record PaymentCompletedEvent(
        String eventId,
        String eventType,
        String timestamp,
        PaymentCompletedData data) {
}
