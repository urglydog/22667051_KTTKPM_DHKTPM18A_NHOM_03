package com.movieticket.userservice.contract;

public record UserRegisteredData(
        String userId,
        String username,
        String email,
        String fullName) {
}
