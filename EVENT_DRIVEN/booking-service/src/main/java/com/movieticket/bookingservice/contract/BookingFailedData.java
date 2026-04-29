package com.movieticket.bookingservice.contract;

public record BookingFailedData(
        String bookingId,
        String reason,
        String status) {
}
