package com.foreveryone.knowing.repository.actionpoint;

import com.foreveryone.knowing.entity.actionpoint.ActionPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionPointRepository extends JpaRepository<ActionPoint, Integer> {
}