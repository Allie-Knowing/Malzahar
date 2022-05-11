package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.request.mypage.InterestCategoriesRequest;
import com.foreveryone.knowing.dto.request.mypage.NicknameRequest;
import com.foreveryone.knowing.dto.response.mypage.FollowResponse;
import com.foreveryone.knowing.dto.response.mypage.InterestResponse;
import com.foreveryone.knowing.entity.mypage.Follow;
import com.foreveryone.knowing.entity.mypage.FollowId;
import com.foreveryone.knowing.entity.mypage.Interest;
import com.foreveryone.knowing.entity.mypage.InterestId;
import com.foreveryone.knowing.entity.auth.User;
import com.foreveryone.knowing.error.exceptions.NotFoundException;
import com.foreveryone.knowing.file.FileUploadProvider;
import com.foreveryone.knowing.repository.mypage.FollowRepository;
import com.foreveryone.knowing.repository.mypage.InterestCategoryRepository;
import com.foreveryone.knowing.repository.mypage.InterestRepository;
import com.foreveryone.knowing.repository.auth.UserRepository;
import com.foreveryone.knowing.security.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final InterestCategoryRepository interestCategoryRepository;
    private final AuthFacade authFacade;
    private final FileUploadProvider fileUploadProvider;
    private final FollowRepository followRepository;

    @Transactional
    public void updateNickname(NicknameRequest nicknameRequest) {
        User user = userRepository.findById(authFacade.getCurrentUserId()).orElseThrow();

        user.updateNickname(nicknameRequest.getNickname());

        System.out.println("닉네임 수정");
    }

    public List<InterestResponse> queryInterests() {
        User currentUser = authFacade.getCurrentUser();
        return interestRepository.findAllByUser(currentUser)
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

    public void updatePicture(MultipartFile file) {
        User currentUser = authFacade.getCurrentUser();

        String fileName = fileUploadProvider.uploadFile(file);

        currentUser.updateProfile(fileName);
        userRepository.save(currentUser);
    }

    public void following(Integer userId) {
        User currentUser = authFacade.getCurrentUser();
        User following = getUserById(userId);

        followRepository.save(Follow.builder()
                .followId(FollowId.builder()
                        .follower(currentUser.getId())
                        .following(following.getId())
                        .build())
                .following(following)
                .follower(currentUser)
                .build());
    }

    public List<FollowResponse> queryFollowing(Integer userId) {
        User currentUser = getUserById(userId);
        return followRepository.findAllByFollower(currentUser).stream()
                .map(follow -> FollowResponse.of(follow.getFollowing()))
                .collect(Collectors.toList());
    }

    public List<FollowResponse> queryFollower(Integer userId) {
        User currentUser = getUserById(userId);
        return followRepository.findAllByFollowing(currentUser).stream()
                .map(follow -> FollowResponse.of(follow.getFollower()))
                .collect(Collectors.toList());
    }

    public void unfollowing(Integer userId) {
        User currentUser = authFacade.getCurrentUser();
        getUserById(userId);                            //user 가 존재하는지 검사
        followRepository.deleteById(FollowId.builder()
                .follower(currentUser.getId())
                .following(userId)
                .build());
    }

    private User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("id : { " + userId + " } User Not Found."));
    }

}
