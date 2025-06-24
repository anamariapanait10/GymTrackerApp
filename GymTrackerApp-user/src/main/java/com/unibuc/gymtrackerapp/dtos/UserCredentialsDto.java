package com.unibuc.gymtrackerapp.dtos;

import jakarta.validation.constraints.NotEmpty;

public record UserCredentialsDto(
        @NotEmpty(message = "Missing username")
        String username,
        @NotEmpty(message = "Missing password")
        String password) {}