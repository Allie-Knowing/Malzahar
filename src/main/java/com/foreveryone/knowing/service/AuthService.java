package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.entity.UserRepository;
import com.foreveryone.knowing.security.JwtTokenProvider;
import com.foreveryone.knowing.oauth.OauthProvider;
import com.foreveryone.knowing.oauth.client.google.GoogleAuthClient;
import com.foreveryone.knowing.oauth.client.google.GoogleUserInfoClient;
import com.foreveryone.knowing.oauth.client.naver.NaverAuthClient;
import com.foreveryone.knowing.oauth.client.naver.NaverUserInfoClient;
import com.foreveryone.knowing.oauth.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.NaverAuthRequest;
import com.foreveryone.knowing.oauth.dto.response.EssentialUserInfo;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleUserInfoResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.foreveryone.knowing.oauth.OauthProvider.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value(value = "${oauth.google.client_id}")
    private String GOOGLE_CLIENT_ID;
    @Value(value = "${oauth.google.client_secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value(value = "${oauth.google.redirect_uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value(value = "${oauth.naver.client_id}")
    private String NAVER_CLIENT_ID;
    @Value(value = "${oauth.naver.client_secret}")
    private String NAVER_CLIENT_SECRET;

    private final UserRepository userRepository;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleUserInfoClient googleUserInfoClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverUserInfoClient naverUserInfoClient;

    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse googleLogin(String code) {

        GoogleAuthRequest googleAuthRequest = GoogleAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(GOOGLE_CLIENT_ID)
                .client_secret(GOOGLE_CLIENT_SECRET)
                .redirect_uri(GOOGLE_REDIRECT_URI)
                .grant_type("authorization_code")
                .build();

        GoogleAuthResponse googleAuthResponse = googleAuthClient.googleAuth(googleAuthRequest);
        String idToken = googleAuthResponse.getIdToken();

        GoogleUserInfoResponse googleUserInfo = googleUserInfoClient.getUserInfo(idToken);

        EssentialUserInfo userInfo = new EssentialUserInfo(
                googleUserInfo.getEmail(),
                googleUserInfo.getPicture(),
                googleUserInfo.getName()
        );

        Integer userId = getUserId(userInfo, GOOGLE);

        return getTokenRes(userId);
    }

    public TokenResponse naverLogin(String code) {

        NaverAuthRequest naverAuthRequest = NaverAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(NAVER_CLIENT_ID)
                .client_secret(NAVER_CLIENT_SECRET)
                .grant_type("authorization_code")
                .build();

        NaverAuthResponse naverAuthResponse = naverAuthClient.naverAuth(naverAuthRequest);
        String accessToken = naverAuthResponse.getAccessToken();

        NaverUserInfoResponse userInfoResponse = naverUserInfoClient.naverUserInfo("Bearer " + accessToken);
        NaverUserInfoResponse.Response response = userInfoResponse.getResponse();

        EssentialUserInfo userInfo = new EssentialUserInfo(
                response.getEmail(),
                response.getProfileImage(),
                response.getName()
        );

        Integer userId = getUserId(userInfo, NAVER);

        return getTokenRes(userId);
    }

    private Integer getUserId(EssentialUserInfo userInfo, OauthProvider provider) {

        String email = userInfo.getEmail();
        String picture = userInfo.getPicture();
        String name = userInfo.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isEmpty()) {
            user = userRepository.save(User.builder()
                    .email(email)
                    .profile(picture)
                    .name(name)
                    .provider(provider)
                    .build());
        } else {
            user = optionalUser.get();
            user.checkProvider(provider);
        }

        return user.getId();
    }

    private TokenResponse getTokenRes(Integer userId) {
        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        return new TokenResponse(accessToken, refreshToken);
    }

}
