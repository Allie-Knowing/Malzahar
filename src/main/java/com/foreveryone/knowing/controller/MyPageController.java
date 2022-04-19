package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.dto.request.NicknameRequest;
import com.foreveryone.knowing.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PutMapping("/nickname")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateNickname(@Valid @RequestBody NicknameRequest nicknameRequest) {
        myPageService.updateNickname(nicknameRequest);
    }
}
