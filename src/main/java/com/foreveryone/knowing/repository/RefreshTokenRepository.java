package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
