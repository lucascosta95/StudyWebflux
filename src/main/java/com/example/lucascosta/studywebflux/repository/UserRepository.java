package com.example.lucascosta.studywebflux.repository;

import com.example.lucascosta.studywebflux.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<UserEntity> save(final UserEntity userEntity) {
        return reactiveMongoTemplate.save(userEntity);
    }

    public Mono<UserEntity> findById(final String id) {
        return reactiveMongoTemplate.findById(id, UserEntity.class);
    }

    public Flux<UserEntity> findAll() {
        return reactiveMongoTemplate.findAll(UserEntity.class);
    }
}
