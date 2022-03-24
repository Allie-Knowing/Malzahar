package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
}