package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.request.NicknameRequest;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.repository.UserRepository;
import com.foreveryone.knowing.security.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final AuthFacade authFacade;

    @Transactional
    public void updateNickname(NicknameRequest nicknameRequest) {
        User user = userRepository.findById(authFacade.getCurrentUserId()).orElseThrow();

        user.updateNickname(nicknameRequest.getNickname());

        System.out.println("닉네임 수정");
    }
}
