package com.example.diploma.repository;

import com.example.diploma.entity.Ad;
import com.example.diploma.entity.Comment;
import com.example.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAd(Ad ad);
    List<Comment> findByAuthor(User author);
}