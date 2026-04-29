package com.movieticket.bookingservice.controller;

import com.movieticket.bookingservice.model.Booking;
import com.movieticket.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingService.CreateBookingRequest request) {
        Booking booking = bookingService.createBooking(request);
        return ResponseEntity.ok(Map.of(
                "message", "Booking created successfully",
                "data", booking));
    }

    @GetMapping
    public ResponseEntity<?> getBookings() {
        return ResponseEntity.ok(Map.of(
                "message", "Bookings retrieved successfully",
                "data", bookingService.findAll()));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable String bookingId) {
        return ResponseEntity.ok(Map.of(
                "message", "Booking retrieved successfully",
                "data", bookingService.findById(bookingId)));
    }
}
