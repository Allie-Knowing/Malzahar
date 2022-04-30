package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.mypage.InterestCategoriesRequest;
import com.foreveryone.knowing.dto.request.mypage.NicknameRequest;
import com.foreveryone.knowing.dto.response.mypage.InterestResponse;
import com.foreveryone.knowing.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateNickname(@Valid @RequestBody NicknameRequest nicknameRequest) {
        System.out.println("닉네임 변경 요청");
        myPageService.updateNickname(nicknameRequest);
    }

    @GetMapping("/interests")
    @ResponseStatus(HttpStatus.OK)
    public List<InterestResponse> queryInterests() {
        System.out.println("관심 분야 요청");
        return myPageService.queryInterests();
    }

    @PostMapping("/interests")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveInterests(@Valid @RequestBody InterestCategoriesRequest interestsRequest) {
        System.out.println("관심 분야 저장/수정 요청");
        myPageService.saveInterests(interestsRequest);
    }

    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePicture(@RequestPart(value = "file")MultipartFile file) {
        myPageService.updatePicture(file);
    }
}
