package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.InterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Integer> {
}