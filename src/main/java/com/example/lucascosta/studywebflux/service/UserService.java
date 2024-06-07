package com.example.lucascosta.studywebflux.service;

import com.example.lucascosta.studywebflux.entity.UserEntity;
import com.example.lucascosta.studywebflux.mapper.UserMapper;
import com.example.lucascosta.studywebflux.model.request.UserRequest;
import com.example.lucascosta.studywebflux.model.response.UserResponse;
import com.example.lucascosta.studywebflux.repository.UserRepository;
import com.example.lucascosta.studywebflux.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public Mono<UserEntity> save(final UserRequest request) {
        return userRepository.save(mapper.toEntity(request));
    }

    public Mono<UserResponse> findById(String id) {
        return handlerNotFound(userRepository.findById(id).map(mapper::toResponse), id);
    }

    public Flux<UserResponse> findAll() {
        return userRepository.findAll().map(mapper::toResponse);
    }

    public Mono<UserResponse> update(final String id, final UserRequest request) {
        return findById(id)
                .map(entity -> mapper.toEntity(request, mapper.toEntity(entity)))
                .flatMap(userRepository::save)
                .map(mapper::toResponse);
    }

    public Mono<UserResponse> delete(final String id) {
        return handlerNotFound(userRepository.findAndRemoveById(id).map(mapper::toResponse), id);
    }

    private <T> Mono<T> handlerNotFound(Mono<T> mono, String id) {
        return mono.switchIfEmpty(Mono.error(new ObjectNotFoundException(format("Object not found. Id: %s, Type: %s", id, UserEntity.class.getSimpleName()))));
    }
}
