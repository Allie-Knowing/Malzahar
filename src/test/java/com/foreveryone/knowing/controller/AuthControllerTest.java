package com.foreveryone.knowing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreveryone.knowing.mocks.FacebookMocks;
import com.foreveryone.knowing.mocks.GoogleMocks;
import com.foreveryone.knowing.WireMockConfig;
import com.foreveryone.knowing.repository.auth.UserRepository;
import com.foreveryone.knowing.mocks.KakaoMocks;
import com.foreveryone.knowing.mocks.NaverMocks;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { WireMockConfig.class })
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WireMockServer mockServer;
    @Autowired
    private ObjectMapper objectMapper;

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

//    @Test
//    void googleLogin() throws Exception {
//        //given
//
//        //when
//        mvc.perform(post("/google")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(new IdTokenRequest("idToken", null))))
//                .andExpect(status().isCreated());
//
//        //then
//        assertThat(userRepository.findAll().size()).isEqualTo(1);
//    }
//
//    @Test
//    void naverLogin() throws Exception {
//        //given
//
//        //when
//        mvc.perform(post("/auth")
//                .param("provider", "NAVER")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(new CodeRequest("code", "redirect_uri"))))
//                .andExpect(status().isCreated());
//
//        //then
//        assertThat(userRepository.findAll().size()).isEqualTo(1);
//    }
//
//    @Test
//    void facebookLogin() throws Exception {
//        //given
//
//        //when
//        mvc.perform(post("/auth")
//                .param("provider", "FACEBOOK")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(new CodeRequest("code", "redirect_uri"))))
//                .andExpect(status().isCreated());
//
//        //then
//        assertThat(userRepository.findAll().size()).isEqualTo(1);
//    }
//
//    @Test
//    void kakaoLogin() throws Exception {
//        //given
//
//        //when
//        mvc.perform(post("/auth")
//                .param("provider", "KAKAO")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(new CodeRequest("code", "redirect_uri"))))
//                .andExpect(status().isCreated());
//
//        //then
//        assertThat(userRepository.findAll().size()).isEqualTo(1);
//    }
}