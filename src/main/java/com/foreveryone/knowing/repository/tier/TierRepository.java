package com.foreveryone.knowing.repository.tier;

import com.foreveryone.knowing.entity.tier.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierRepository extends JpaRepository<Tier, Integer> {
}