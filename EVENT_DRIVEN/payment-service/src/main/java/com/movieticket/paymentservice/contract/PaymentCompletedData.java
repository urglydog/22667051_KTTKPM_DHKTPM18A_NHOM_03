package com.movieticket.paymentservice.contract;

public record PaymentCompletedData(
        String bookingId,
        String transactionId,
        String paymentMethod,
        Double amount,
        String status) {
}
