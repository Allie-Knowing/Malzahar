package com.foreveryone.knowing.repository.mypage;

import com.foreveryone.knowing.entity.mypage.Interest;
import com.foreveryone.knowing.entity.mypage.InterestId;
import com.foreveryone.knowing.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, InterestId> {
    List<Interest> findAllByUser(User user);
}