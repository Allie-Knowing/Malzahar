package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}