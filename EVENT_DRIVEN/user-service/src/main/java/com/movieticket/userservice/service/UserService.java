package com.movieticket.userservice.service;

import com.movieticket.userservice.contract.UserRegisteredData;
import com.movieticket.userservice.contract.UserRegisteredEvent;
import com.movieticket.userservice.publisher.UserEventPublisher;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<String, UserAccount> users = new ConcurrentHashMap<>();
    private final UserEventPublisher userEventPublisher;

    public UserService(UserEventPublisher userEventPublisher) {
        this.userEventPublisher = userEventPublisher;
    }

    public UserAccount register(RegisterRequest request) {
        String normalizedUsername = request.username().trim().toLowerCase();
        if (users.containsKey(normalizedUsername)) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserAccount account = new UserAccount(
                UUID.randomUUID().toString(),
                request.username().trim(),
                request.email().trim().toLowerCase(),
                request.fullName().trim());
        users.put(normalizedUsername, account);

        userEventPublisher.publishUserRegistered(
                new UserRegisteredEvent(
                        "EVT-U-" + UUID.randomUUID(),
                        "USER_REGISTERED",
                        Instant.now().toString(),
                        new UserRegisteredData(
                                account.id(),
                                account.username(),
                                account.email(),
                                account.fullName())));
        return account;
    }

    public UserAccount login(LoginRequest request) {
        String normalizedUsername = request.username().trim().toLowerCase();
        UserAccount account = users.get(normalizedUsername);
        if (account == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return account;
    }

    public record RegisterRequest(
            @NotBlank String username,
            @NotBlank @Email String email,
            @NotBlank String fullName,
            @NotBlank String password) {
    }

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record UserAccount(
            String id,
            String username,
            String email,
            String fullName) {
    }
}
