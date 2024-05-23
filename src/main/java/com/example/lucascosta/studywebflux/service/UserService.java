package com.example.lucascosta.studywebflux.service;

import com.example.lucascosta.studywebflux.entity.UserEntity;
import com.example.lucascosta.studywebflux.mapper.UserMapper;
import com.example.lucascosta.studywebflux.model.request.UserRequest;
import com.example.lucascosta.studywebflux.model.response.UserResponse;
import com.example.lucascosta.studywebflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;


    public Mono<UserEntity> save(final UserRequest request) {
        return userRepository.save(mapper.toEntity(request));
    }

    public Mono<UserResponse> findById(String id) {
        return userRepository.findById(id).map(mapper::toResponse);
    }

    public Flux<UserResponse> findAll() {
        return userRepository.findAll().map(mapper::toResponse);
    }
}
