package com.example.diploma.mapper;

import com.example.diploma.entity.Image;
import com.example.diploma.entity.User;
import com.example.diploma.dto.RegisterReq;
import com.example.diploma.dto.UpdateUserDto;
import com.example.diploma.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "imageToString")
    @Mapping(target = "role", source = "role") // <-- Добавили передачу роли на фронтенд
    UserDto toDto(User user);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "image", ignore = true)
    User toEntity(RegisterReq registerReq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "image", ignore = true)
    User updateToEntity(UpdateUserDto updateDto, @org.mapstruct.MappingTarget User user);

    @Named("imageToString")
    default String imageToString(Image image) {
        if (image == null) {
            return null;
        }
        return "/users/me/image/" + image.getId();
    }
}