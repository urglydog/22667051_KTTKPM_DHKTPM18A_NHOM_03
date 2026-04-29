package com.movieticket.bookingservice.model;

import java.time.Instant;

public class Booking {

    private String bookingId;
    private String userId;
    private String movieId;
    private String seatNumber;
    private double amount;
    private BookingStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public Booking() {
    }

    public Booking(String bookingId, String userId, String movieId, String seatNumber, double amount, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.seatNumber = seatNumber;
        this.amount = amount;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
