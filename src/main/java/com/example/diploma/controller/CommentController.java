package com.example.diploma.controller;

import com.example.diploma.dto.CommentDto;
import com.example.diploma.dto.CommentsDto;
import com.example.diploma.dto.CreateCommentDto;
import com.example.diploma.security.CustomUserDetails;
import com.example.diploma.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Long adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Long adId,
                                                 @RequestBody CreateCommentDto commentDto,
                                                 Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(commentService.addComment(adId, commentDto, userId));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long adId,
                                              @PathVariable Long commentId,
                                              Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean isAdmin = checkIsAdmin(authentication);
        commentService.deleteComment(adId, commentId, userId, isAdmin);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long adId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody CreateCommentDto commentDto,
                                                    Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean isAdmin = checkIsAdmin(authentication);
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, commentDto, userId, isAdmin));
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser().getId();
    }

    private boolean checkIsAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}