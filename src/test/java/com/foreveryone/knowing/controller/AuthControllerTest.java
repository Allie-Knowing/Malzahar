package com.foreveryone.knowing.controller;

import com.foreveryone.knowing.GoogleMocks;
import com.foreveryone.knowing.WireMockConfig;
import com.foreveryone.knowing.entity.UserRepository;
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
    private WireMockServer mockGoogleAuthServer;

    @BeforeEach
    void setUp() throws IOException {
        GoogleMocks.setUpMockGoogleAuthResponse(mockGoogleAuthServer);
        GoogleMocks.setUpMockGoogleUserInfoResponse(mockGoogleAuthServer);
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
    void naverLogin() {
    }

    @Test
    void facebookLogin() {
    }

    @Test
    void kakaoLogin() {
    }
}