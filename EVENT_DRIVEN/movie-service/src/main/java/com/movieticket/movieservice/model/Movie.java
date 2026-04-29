package com.movieticket.movieservice.model;

public record Movie(
        String id,
        String title,
        String genre,
        String duration,
        String description,
        double price) {
}
