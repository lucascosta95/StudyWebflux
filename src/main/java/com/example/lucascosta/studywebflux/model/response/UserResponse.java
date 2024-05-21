package com.example.lucascosta.studywebflux.model.response;

public record UserResponse(
        String id,
        String name,
        String email,
        String password
) {}
