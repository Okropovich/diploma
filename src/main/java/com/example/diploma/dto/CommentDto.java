package com.example.diploma.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long author;          // ID автора (Long)
    private String authorImage;   // Ссылка или ID изображения
    private String authorFirstName;
    private Long createdAt;       // Timestamp в миллисекундах
    private Long pk;              // ID комментария (Long)
    private String text;
}