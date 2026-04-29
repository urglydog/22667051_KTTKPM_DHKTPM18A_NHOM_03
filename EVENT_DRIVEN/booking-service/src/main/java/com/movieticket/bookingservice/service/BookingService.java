package com.movieticket.bookingservice.service;

import com.movieticket.bookingservice.contract.BookingCreatedData;
import com.movieticket.bookingservice.contract.BookingCreatedEvent;
import com.movieticket.bookingservice.contract.BookingFailedEvent;
import com.movieticket.bookingservice.contract.PaymentCompletedEvent;
import com.movieticket.bookingservice.model.Booking;
import com.movieticket.bookingservice.model.BookingStatus;
import com.movieticket.bookingservice.publisher.BookingEventPublisher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {

    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();
    private final BookingEventPublisher bookingEventPublisher;

    public BookingService(BookingEventPublisher bookingEventPublisher) {
        this.bookingEventPublisher = bookingEventPublisher;
    }

    public Booking createBooking(CreateBookingRequest request) {
        String bookingId = UUID.randomUUID().toString();
        Booking booking = new Booking(
                bookingId,
                request.userId().trim(),
                request.movieId().trim(),
                request.seatNumber().trim(),
                request.amount(),
                BookingStatus.PENDING);
        bookings.put(bookingId, booking);

        bookingEventPublisher.publishBookingCreated(
                new BookingCreatedEvent(
                        "EVT-B-" + UUID.randomUUID(),
                        "BOOKING_CREATED",
                        Instant.now().toString(),
                        new BookingCreatedData(
                    booking.getBookingId(),
                                booking.getUserId(),
                                booking.getMovieId(),
                                booking.getSeatNumber(),
                                booking.getAmount(),
                                booking.getStatus().name())));
        return booking;
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public Booking findById(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        return booking;
    }

    public void markPaymentCompleted(PaymentCompletedEvent event) {
        if (event == null || event.data() == null || event.data().bookingId() == null) {
            return;
        }
        Booking booking = bookings.get(event.data().bookingId());
        if (booking != null) {
            booking.setStatus(BookingStatus.COMPLETED);
            booking.setUpdatedAt(Instant.now());
        }
    }

    public void markBookingFailed(BookingFailedEvent event) {
        if (event == null || event.data() == null || event.data().bookingId() == null) {
            return;
        }
        Booking booking = bookings.get(event.data().bookingId());
        if (booking != null) {
            booking.setStatus(BookingStatus.FAILED);
            booking.setUpdatedAt(Instant.now());
        }
    }

    public record CreateBookingRequest(
            @NotBlank String userId,
            @NotBlank String movieId,
            @NotBlank String seatNumber,
            @Positive double amount) {
    }
}
