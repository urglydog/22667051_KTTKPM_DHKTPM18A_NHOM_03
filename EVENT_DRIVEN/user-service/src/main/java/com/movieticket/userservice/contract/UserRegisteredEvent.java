package com.movieticket.userservice.contract;

public record UserRegisteredEvent(
        String eventId,
        String eventType,
        String timestamp,
        UserRegisteredData data) {
}
