package com.foreveryone.knowing.repository.admin.answer;

import com.foreveryone.knowing.entity.admin.answer.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}