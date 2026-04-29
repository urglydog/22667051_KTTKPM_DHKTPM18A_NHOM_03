package com.movieticket.paymentservice.contract;

public record BookingCreatedData(
        String bookingId,
        String userId,
        String cinema,
        String movieId,
        String seatNumber,
        Double amount,
        String status) {
}
