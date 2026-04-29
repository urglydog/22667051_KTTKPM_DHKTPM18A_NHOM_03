package com.movieticket.notificationservice.contract;

public record PaymentCompletedData(
        String bookingId,
        String transactionId,
        String paymentMethod,
        Double amount,
        String status) {
}
