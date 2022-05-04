package com.foreveryone.knowing.repository.tier;

import com.foreveryone.knowing.entity.tier.TierCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierCategoryRepository extends JpaRepository<TierCategory, Integer> {
}