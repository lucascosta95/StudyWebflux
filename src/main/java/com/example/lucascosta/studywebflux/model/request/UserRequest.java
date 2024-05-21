package com.example.lucascosta.studywebflux.model.request;

public record UserRequest(
        String name,
        String email,
        String password
) {}
