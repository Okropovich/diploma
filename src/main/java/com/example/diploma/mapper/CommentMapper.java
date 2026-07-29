package com.example.diploma.mapper;

import com.example.diploma.dto.CommentDto;
import com.example.diploma.dto.CommentsDto;
import com.example.diploma.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", source = "author.image.id", qualifiedByName = "imageToString")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "localDateTimeToLong")
    CommentDto toCommentDto(Comment comment);

    CommentsDto toCommentsDto(Integer count, List<Comment> results);


    @Named("imageToString")
    default String imageToString(Long imageId) {
        return imageId != null ? "/images/" + imageId : null;
    }


    @Named("localDateTimeToLong")
    default Long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
    }
}