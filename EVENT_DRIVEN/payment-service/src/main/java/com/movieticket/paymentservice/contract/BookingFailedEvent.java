package com.movieticket.paymentservice.contract;

public record BookingFailedEvent(
        String eventId,
        String eventType,
        String timestamp,
        BookingFailedData data) {
}
