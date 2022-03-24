package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.mocks.FacebookMocks;
import com.foreveryone.knowing.mocks.GoogleMocks;
import com.foreveryone.knowing.WireMockConfig;
import com.foreveryone.knowing.repository.UserRepository;
import com.foreveryone.knowing.mocks.KakaoMocks;
import com.foreveryone.knowing.mocks.NaverMocks;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = { WireMockConfig.class })
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WireMockServer mockServer;

    @BeforeEach
    void setUp() throws IOException {
        GoogleMocks.setUpMockGoogleAuthResponse(mockServer);
        GoogleMocks.setUpMockGoogleUserInfoResponse(mockServer);
        FacebookMocks.setUpMockFacebookAuthResponse(mockServer);
        FacebookMocks.setUpMockFacebookUserInfoResponse(mockServer);
        NaverMocks.setUpMockNaverAuthResponse(mockServer);
        NaverMocks.setUpMockNaverUserInfoResponse(mockServer);
        KakaoMocks.setUpMockKakaoAuthResponse(mockServer);
        KakaoMocks.setUpMockKakaoUserInfoResponse(mockServer);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void googleLogin() throws Exception {
        //given

        //when
        mvc.perform(post("/auth")
                        .param("provider", "GOOGLE")
                        .param("code", "code"))
                .andExpect(status().isCreated());

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void naverLogin() throws Exception {
        //given

        //when
        mvc.perform(post("/auth")
                        .param("provider", "NAVER")
                        .param("code", "code"))
                .andExpect(status().isCreated());

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void facebookLogin() throws Exception {
        //given

        //when
        mvc.perform(post("/auth")
                        .param("provider", "FACEBOOK")
                        .param("code", "code"))
                .andExpect(status().isCreated());

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void kakaoLogin() throws Exception {
        //given

        //when
        mvc.perform(post("/auth")
                        .param("provider", "KAKAO")
                        .param("code", "code"))
                .andExpect(status().isCreated());

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }
}