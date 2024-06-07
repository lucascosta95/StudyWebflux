package com.example.lucascosta.studywebflux.mapper;

import com.example.lucascosta.studywebflux.entity.UserEntity;
import com.example.lucascosta.studywebflux.model.request.UserRequest;
import com.example.lucascosta.studywebflux.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(final UserRequest request);

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(final UserRequest request, @MappingTarget final UserEntity entity);

    UserEntity toEntity(UserResponse response);

    UserResponse toResponse(final UserEntity userEntity);
}
