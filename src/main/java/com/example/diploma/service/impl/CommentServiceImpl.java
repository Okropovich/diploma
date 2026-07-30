package com.example.diploma.service.impl;

import com.example.diploma.dto.CommentDto;
import com.example.diploma.dto.CommentsDto;
import com.example.diploma.dto.CreateCommentDto;
import com.example.diploma.entity.Ad;
import com.example.diploma.entity.Comment;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.CommentMapper;
import com.example.diploma.repository.CommentRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.AdService;
import com.example.diploma.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdService adService;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentsDto getComments(Long adId) {
        Ad ad = adService.getAdEntityById(adId);
        List<Comment> comments = commentRepository.findByAd(ad);
        return commentMapper.toCommentsDto(comments.size(), comments);
    }

    @Override
    @Transactional
    public CommentDto addComment(Long adId, CreateCommentDto commentDto, Long userId) {
        Ad ad = adService.getAdEntityById(adId);
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAd(ad);
        comment.setAuthor(author);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long adId, Long commentId, Long userId, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        checkOwnerOrAdmin(comment, userId, isAdmin);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long adId, Long commentId, CreateCommentDto commentDto, Long userId, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        checkOwnerOrAdmin(comment, userId, isAdmin);
        comment.setText(commentDto.getText());

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentDto(updatedComment);
    }

    private void checkOwnerOrAdmin(Comment comment, Long userId, boolean isAdmin) {
        if (!isAdmin && (comment.getAuthor() == null || !comment.getAuthor().getId().equals(userId))) {
            throw new AccessDeniedException("You don't have permission to modify this comment");
        }
    }
}