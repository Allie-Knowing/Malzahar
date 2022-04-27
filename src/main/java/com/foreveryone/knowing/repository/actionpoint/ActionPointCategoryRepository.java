package com.foreveryone.knowing.repository.actionpoint;

import com.foreveryone.knowing.entity.actionpoint.ActionPointCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionPointCategoryRepository extends JpaRepository<ActionPointCategory, Integer> {
}