package com.movieticket.bookingservice.contract;

public record BookingCreatedEvent(
        String eventId,
        String eventType,
        String timestamp,
        BookingCreatedData data) {
}
