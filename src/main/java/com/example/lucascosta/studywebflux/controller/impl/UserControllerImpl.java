package com.example.lucascosta.studywebflux.controller.impl;

import com.example.lucascosta.studywebflux.controller.UserController;
import com.example.lucascosta.studywebflux.model.request.UserRequest;
import com.example.lucascosta.studywebflux.model.response.UserResponse;
import com.example.lucascosta.studywebflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<Mono<UserResponse>> findById(String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @Override
    public ResponseEntity<Flux<UserResponse>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Override
    public ResponseEntity<Mono<Void>> save(UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(request).then());
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
        return ResponseEntity.ok().body(userService.update(id, request));
    }

    @Override
    public ResponseEntity<Mono<Void>> deleteById(String id) {
        return ResponseEntity.ok().body(userService.delete(id).then());
    }
}
