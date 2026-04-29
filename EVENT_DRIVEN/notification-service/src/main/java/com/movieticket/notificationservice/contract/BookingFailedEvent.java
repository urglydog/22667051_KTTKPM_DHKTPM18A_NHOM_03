package com.movieticket.notificationservice.contract;

public record BookingFailedEvent(
        String eventId,
        String eventType,
        String timestamp,
        BookingFailedData data) {
}
