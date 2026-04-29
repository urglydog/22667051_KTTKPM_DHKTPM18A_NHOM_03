package com.movieticket.movieservice.service;

import com.movieticket.movieservice.model.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MovieService {

    private final Map<String, Movie> movies = new ConcurrentHashMap<>();

    public MovieService() {
        Movie seed = new Movie(
                UUID.randomUUID().toString(),
                "Avengers: Endgame",
                "Action",
                "181 min",
                "Superhero blockbuster",
                120000);
        movies.put(seed.id(), seed);
    }

    public List<Movie> findAll() {
        return new ArrayList<>(movies.values());
    }

    public Movie save(MovieRequest request) {
        Movie movie = new Movie(
                request.id() == null || request.id().isBlank() ? UUID.randomUUID().toString() : request.id(),
                request.title().trim(),
                request.genre().trim(),
                request.duration().trim(),
                request.description().trim(),
                request.price());
        movies.put(movie.id(), movie);
        return movie;
    }

    public record MovieRequest(
            String id,
            @NotBlank String title,
            @NotBlank String genre,
            @NotBlank String duration,
            @NotBlank String description,
            @Positive double price) {
    }
}
