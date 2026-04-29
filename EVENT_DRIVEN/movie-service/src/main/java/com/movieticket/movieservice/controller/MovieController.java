package com.movieticket.movieservice.controller;

import com.movieticket.movieservice.model.Movie;
import com.movieticket.movieservice.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovies() {
        return ResponseEntity.ok(Map.of(
                "message", "Movies retrieved successfully",
                "data", movieService.findAll()));
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieService.MovieRequest request) {
        Movie movie = movieService.save(request);
        return ResponseEntity.ok(Map.of(
                "message", "Movie saved successfully",
                "data", movie));
    }
}
