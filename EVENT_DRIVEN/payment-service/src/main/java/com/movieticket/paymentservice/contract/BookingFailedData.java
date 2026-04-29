package com.movieticket.paymentservice.contract;

public record BookingFailedData(
        String bookingId,
        String reason,
        String status) {
}
