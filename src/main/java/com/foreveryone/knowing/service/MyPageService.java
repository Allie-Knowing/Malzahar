package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.request.InterestCategoriesRequest;
import com.foreveryone.knowing.dto.request.NicknameRequest;
import com.foreveryone.knowing.dto.response.InterestResponse;
import com.foreveryone.knowing.entity.Interest;
import com.foreveryone.knowing.entity.InterestId;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.error.exceptions.NotFoundException;
import com.foreveryone.knowing.repository.InterestCategoryRepository;
import com.foreveryone.knowing.repository.InterestRepository;
import com.foreveryone.knowing.repository.UserRepository;
import com.foreveryone.knowing.security.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final InterestCategoryRepository interestCategoryRepository;
    private final AuthFacade authFacade;

    @Transactional
    public void updateNickname(NicknameRequest nicknameRequest) {
        User user = userRepository.findById(authFacade.getCurrentUserId()).orElseThrow();

        user.updateNickname(nicknameRequest.getNickname());

        System.out.println("닉네임 수정");
    }

    public List<InterestResponse> queryInterests() {
        return interestCategoryRepository.findAll()
                        .stream().map(InterestResponse::of)
                        .collect(Collectors.toList());
    }

    public void saveInterests(InterestCategoriesRequest interestsRequest) {
        User currentUser = authFacade.getCurrentUser();

        List<Integer> myInterests = interestRepository.findAllByUser(currentUser)
                .stream().map(interest -> interest.getCategory().getId())
                .collect(Collectors.toList());

        interestsRequest.getInterestCategories().forEach(interestCategoryId -> {
            if (!myInterests.contains(interestCategoryId)) {
                interestRepository.save(
                        Interest.builder()
                                .interestId(
                                        InterestId.builder()
                                                .categoryId(interestCategoryId)
                                                .userId(currentUser.getId())
                                                .build())
                                .category(interestCategoryRepository.findById(interestCategoryId)
                                        .orElseThrow(() -> new NotFoundException("관심분야 카테고리 Id: " + interestCategoryId + " 를 찾을 수 없습니다.")))
                                .user(currentUser)
                                .build());
            }
            myInterests.remove(interestCategoryId);
        });

        myInterests.forEach(interestCategoryId ->
            interestRepository.deleteById(
                    InterestId.builder()
                    .categoryId(interestCategoryId)
                    .userId(currentUser.getId())
                    .build()
            )
        );
        System.out.println("관심 분야 수정 완료");
    }
}
