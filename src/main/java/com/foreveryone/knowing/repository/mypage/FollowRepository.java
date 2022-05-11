package com.foreveryone.knowing.repository.mypage;

import com.foreveryone.knowing.entity.auth.User;
import com.foreveryone.knowing.entity.mypage.Follow;
import com.foreveryone.knowing.entity.mypage.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findAllByFollowing(User user);
    List<Follow> findAllByFollower(User user);
}