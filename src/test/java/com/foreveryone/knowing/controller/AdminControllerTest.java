package com.foreveryone.knowing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreveryone.knowing.dto.request.InquiryRequest;
import com.foreveryone.knowing.dto.request.ReportRequest;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.entity.Video;
import com.foreveryone.knowing.oauth.OauthProvider;
import com.foreveryone.knowing.repository.*;
import com.foreveryone.knowing.security.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    InquiryRepository inquiryRepository;
    @Autowired
    InquiryCategoryRepository inquiryCategoryRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    ObjectMapper objectMapper;

    User user;
    Video video;

    @BeforeEach
    void setUp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user = userRepository.save(User.builder()
                        .email("kwak@email")
                        .name("name")
                        .profile("profile_image")
                        .provider(OauthProvider.GOOGLE)
                        .createdAt(now)
                .updatedAt(now)
                .lastAccessedAt(now)
                .build()
        );
        video = videoRepository.save(Video.builder()
                .user(user)
                .build());
    }

    @AfterEach
    void tearDown() {
        inquiryCategoryRepository.deleteAll();
        reportRepository.deleteAll();
        videoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void submitReport() throws Exception {
        mvc.perform(post("/admin/report")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ReportRequest(video.getId(), "신고합니다.")))
                .header("Authorization", "Bearer " + jwtTokenProvider.generateAccessToken(user.getId())))
                .andExpect(status().isCreated());
    }

    @Test
    void reportList() throws Exception {
        mvc.perform(get("/admin/report"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteVideo() throws Exception {
        mvc.perform(delete("/admin/video/" + video.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void inquiry() throws Exception {
        mvc.perform(post("/admin/inquiry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new InquiryRequest("BUG", "버그가 너무 많음", "쨋든 많음")))
                        .header("Authorization", "Bearer " + jwtTokenProvider.generateAccessToken(user.getId())))
                .andExpect(status().isCreated());
    }

    @Test
    void inquiryList() throws Exception {
        mvc.perform(get("/admin/inquiry"))
                .andExpect(status().isOk());
    }
}