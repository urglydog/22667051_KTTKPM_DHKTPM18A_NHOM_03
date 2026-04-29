package com.movieticket.notificationservice.contract;

public record BookingFailedData(
        String bookingId,
        String reason,
        String status) {
}
