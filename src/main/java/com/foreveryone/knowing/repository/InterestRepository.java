package com.foreveryone.knowing.repository;

import com.foreveryone.knowing.entity.Interest;
import com.foreveryone.knowing.entity.InterestId;
import com.foreveryone.knowing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, InterestId> {
    List<Interest> findAllByUser(User user);
}