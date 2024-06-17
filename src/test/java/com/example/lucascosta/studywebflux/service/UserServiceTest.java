package com.example.lucascosta.studywebflux.service;

import com.example.lucascosta.studywebflux.entity.UserEntity;
import com.example.lucascosta.studywebflux.mapper.UserMapper;
import com.example.lucascosta.studywebflux.model.request.UserRequest;
import com.example.lucascosta.studywebflux.model.response.UserResponse;
import com.example.lucascosta.studywebflux.repository.UserRepository;
import com.example.lucascosta.studywebflux.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testSave() {
        var request = new UserRequest("Any Name", "any_email@any.com", "anyPassword");
        var entity = UserEntity.builder().build();

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(UserEntity.builder().build()));

        var result = userService.save(request);
        StepVerifier
                .create(result)
                .expectNextMatches(UserEntity.class::isInstance)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testFindById() {
        when(userRepository.findById(anyString())).thenReturn(Mono.just(UserEntity.builder().build()));
        when(userMapper.toResponse(any(UserEntity.class))).thenReturn(new UserResponse("any_id", "Any Name", "any_email@any.com", "anyPassword"));

        var result = userService.findById("any_id");
        StepVerifier.create(result)
                .expectNextMatches(UserResponse.class::isInstance)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(UserEntity.builder().build()));
        when(userMapper.toResponse(any(UserEntity.class))).thenReturn(new UserResponse("any_id", "Any Name", "any_email@any.com", "anyPassword"));

        var result = userService.findAll();
        StepVerifier.create(result)
                .expectNextMatches(UserResponse.class::isInstance)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        var request = new UserRequest("Any Name", "any_email@any.com", "anyPassword");
        var entity = UserEntity.builder().build();

        when(userRepository.findById(anyString())).thenReturn(Mono.just(UserEntity.builder().build()));

        when(userMapper.toEntity(any(UserRequest.class), any(UserEntity.class))).thenReturn(entity);
        when(userMapper.toEntity(any(UserResponse.class))).thenReturn(entity);

        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(entity));
        when(userMapper.toResponse(any(UserEntity.class))).thenReturn(new UserResponse("any_id", "Any Name", "any_email@any.com", "anyPassword"));

        var result = userService.update("any_id", request);
        StepVerifier.create(result)
                .expectNextMatches(UserResponse.class::isInstance)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testDelete() {
        var entity = UserEntity.builder().build();
        when(userRepository.findAndRemoveById(anyString())).thenReturn(Mono.just(entity));
        when(userMapper.toResponse(any(UserEntity.class))).thenReturn(new UserResponse("any_id", "Any Name", "any_email@any.com", "anyPassword"));

        var result = userService.delete("any_id");
        StepVerifier.create(result)
                .expectNextMatches(UserResponse.class::isInstance)
                .expectComplete()
                .verify();

        Mockito.verify(userRepository, times(1)).findAndRemoveById(anyString());
    }

    @Test
    void testHandleNotFound() {
        when(userRepository.findById(anyString())).thenReturn(Mono.empty());
        try {
            userService.findById("any_id").block();
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(format("Object not found. Id: %s, Type: %s", "any_id", UserEntity.class.getSimpleName()), ex.getMessage());
        }
    }
}