package com.movieticket.bookingservice.contract;

public record BookingFailedEvent(
        String eventId,
        String eventType,
        String timestamp,
        BookingFailedData data) {
}
