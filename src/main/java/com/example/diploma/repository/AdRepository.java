package com.example.diploma.repository;

import com.example.diploma.entity.Ad;
import com.example.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findByAuthor(User author);
}