package com.movieticket.bookingservice.contract;

public record BookingCreatedData(
        String bookingId,
        String userId,
        String movieId,
        String seatNumber,
        Double amount,
        String status) {
}
