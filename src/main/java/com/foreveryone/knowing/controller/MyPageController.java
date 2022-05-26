package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.mypage.FollowingRequest;
import com.foreveryone.knowing.dto.request.mypage.InterestCategoriesRequest;
import com.foreveryone.knowing.dto.request.mypage.NicknameRequest;
import com.foreveryone.knowing.dto.response.mypage.BooleanResponse;
import com.foreveryone.knowing.dto.response.mypage.FollowResponse;
import com.foreveryone.knowing.dto.response.mypage.InterestResponse;
import com.foreveryone.knowing.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("isAuthenticated()")
    public void updateNickname(@Valid @RequestBody NicknameRequest nicknameRequest) {
        System.out.println("닉네임 변경 요청");
        myPageService.updateNickname(nicknameRequest);
    }

    @GetMapping("/interests")
    @ResponseStatus(HttpStatus.OK) @PreAuthorize("isAuthenticated()")
    public List<InterestResponse> queryInterests() {
        System.out.println("관심 분야 요청");
        return myPageService.queryInterests();
    }

    @PostMapping("/interests")
    @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("isAuthenticated()")
    public void saveInterests(@Valid @RequestBody InterestCategoriesRequest interestsRequest) {
        System.out.println("관심 분야 저장/수정 요청");
        myPageService.saveInterests(interestsRequest);
    }

    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("isAuthenticated()")
    public void updatePicture(@RequestPart(value = "file")MultipartFile file) {
        System.out.println("프로필 수정");
        myPageService.updatePicture(file);
    }

    @PostMapping("/following")
    @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("isAuthenticated()")
    public void following(@Valid @RequestBody FollowingRequest request) {
        System.out.println("팔로잉");
        myPageService.following(request.getUserId());
    }

    @DeleteMapping("/following")
    @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("isAuthenticated()")
    public void unfollowing(@Valid @RequestBody FollowingRequest request) {
        System.out.println("언팔로잉");
        myPageService.unfollowing(request.getUserId());
    }

    @GetMapping("/follower")
    @ResponseStatus(HttpStatus.OK) @PreAuthorize("isAuthenticated()")
    public List<FollowResponse> queryFollowers(@RequestParam Integer userId) {
        System.out.println("팔로워 리스트");
        return myPageService.queryFollower(userId);
    }

    @GetMapping("/following")
    @ResponseStatus(HttpStatus.OK) @PreAuthorize("isAuthenticated()")
    public List<FollowResponse> queryFollowing(@RequestParam Integer userId) {
        System.out.println("팔로잉 리스트");
        return myPageService.queryFollowing(userId);
    }

    @GetMapping("/following/{userId}")
    @ResponseStatus(HttpStatus.OK) @PreAuthorize("isAuthenticated()")
    public BooleanResponse checkFollowing(@PathVariable Integer userId) {
        return myPageService.checkFollowing(userId);
    }
}
