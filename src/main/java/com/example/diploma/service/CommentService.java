package com.example.diploma.service;

import com.example.diploma.dto.CommentDto;
import com.example.diploma.dto.CommentsDto;
import com.example.diploma.dto.CreateCommentDto;

public interface CommentService {

    CommentsDto getComments(Long adId);

    CommentDto addComment(Long adId, CreateCommentDto commentDto, Long userId);

    void deleteComment(Long adId, Long commentId, Long userId, boolean isAdmin);

    CommentDto updateComment(Long adId, Long commentId, CreateCommentDto commentDto, Long userId, boolean isAdmin);
}