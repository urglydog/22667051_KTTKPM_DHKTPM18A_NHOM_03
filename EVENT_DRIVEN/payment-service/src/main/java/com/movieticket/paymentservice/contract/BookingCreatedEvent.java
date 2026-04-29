package com.movieticket.paymentservice.contract;

public record BookingCreatedEvent(
        String eventId,
        String eventType,
        String timestamp,
        BookingCreatedData data) {
}
