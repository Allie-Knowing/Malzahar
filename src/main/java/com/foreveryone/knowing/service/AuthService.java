package com.foreveryone.knowing.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.repository.UserRepository;
import com.foreveryone.knowing.oauth.OauthRequestDtoBuilder;
import com.foreveryone.knowing.oauth.client.facebook.FacebookAuthClient;
import com.foreveryone.knowing.oauth.client.facebook.FacebookUserInfoClient;
import com.foreveryone.knowing.oauth.client.kakao.KakaoAuthClient;
import com.foreveryone.knowing.oauth.client.kakao.KakaoUserInfoClient;
import com.foreveryone.knowing.oauth.dto.request.*;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookUserInfoResponse;
import com.foreveryone.knowing.oauth.dto.response.kakao.KakaoAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.kakao.KakaoUserInfoResponse;
import com.foreveryone.knowing.security.JwtTokenProvider;
import com.foreveryone.knowing.oauth.client.google.GoogleAuthClient;
import com.foreveryone.knowing.oauth.client.google.GoogleUserInfoClient;
import com.foreveryone.knowing.oauth.client.naver.NaverAuthClient;
import com.foreveryone.knowing.oauth.client.naver.NaverUserInfoClient;
import com.foreveryone.knowing.oauth.dto.EssentialUserInfo;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleUserInfoResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;

import static com.foreveryone.knowing.oauth.OauthProvider.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleUserInfoClient googleUserInfoClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverUserInfoClient naverUserInfoClient;
    private final FacebookAuthClient facebookAuthClient;
    private final FacebookUserInfoClient facebookUserInfoClient;
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;

    private final OauthRequestDtoBuilder oauthDtoBuilder;

    private final JwtTokenProvider jwtTokenProvider;


    public TokenResponse googleLogin(String code) {

        GoogleAuthRequest googleAuthRequest = oauthDtoBuilder.getGoogle(code);

        GoogleAuthResponse googleAuthResponse = googleAuthClient.googleAuth(googleAuthRequest);
        String idToken = googleAuthResponse.getIdToken();

        GoogleUserInfoResponse googleUserInfo = googleUserInfoClient.getUserInfo(idToken);

        EssentialUserInfo userInfo = new EssentialUserInfo(
                googleUserInfo.getEmail(),
                googleUserInfo.getPicture(),
                googleUserInfo.getName(),
                GOOGLE
        );

        Integer userId = getUserId(userInfo);

        return getTokenResponse(userId);
    }


    public TokenResponse naverLogin(String code) {

        NaverAuthRequest naverAuthRequest = oauthDtoBuilder.getNaver(code);

        NaverAuthResponse naverAuthResponse = naverAuthClient.naverAuth(naverAuthRequest);
        String accessToken = naverAuthResponse.getAccessToken();

        NaverUserInfoResponse userInfoResponse = naverUserInfoClient.naverUserInfo("Bearer " + accessToken);
        NaverUserInfoResponse.Response response = userInfoResponse.getResponse();

        EssentialUserInfo userInfo = new EssentialUserInfo(
                response.getEmail(),
                response.getProfileImage(),
                response.getName(),
                NAVER
        );

        Integer userId = getUserId(userInfo);

        return getTokenResponse(userId);
    }


    public TokenResponse facebookLogin(String code) {

        FacebookAuthRequest facebookAuthRequest = oauthDtoBuilder.getFacebook(code);

        FacebookAuthResponse facebookAuthResponse = facebookAuthClient.facebookAuth(facebookAuthRequest);
        String accessToken = facebookAuthResponse.getAccessToken();

        FacebookUserInfoResponse facebookUserInfo = facebookUserInfoClient.facebookUserInfo(FacebookUserInfoRequest.builder()
                .fields("name,email,picture")
                .access_token(accessToken)
                .build()
        );

        EssentialUserInfo userInfo = new EssentialUserInfo(
                facebookUserInfo.getEmail(),
                facebookUserInfo.getPicture().getData().getUrl(),
                facebookUserInfo.getName(),
                FACEBOOK
        );

        Integer userId = getUserId(userInfo);

        return getTokenResponse(userId);
    }

    public TokenResponse kakaoLogin(String code) {
        KakaoAuthRequest kakaoAuthRequest = oauthDtoBuilder.getKakao(code);

        // Content-Type 을 application/x-www-form-urlencoded 로 설정하기 위해 MultiValueMap 사용
        // 참고 : https://jojoldu.tistory.com/478

        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> map = objectMapper.convertValue(kakaoAuthRequest, new TypeReference<>() {});
        params.setAll(map);

        KakaoAuthResponse kakaoAuthResponse = kakaoAuthClient.kakaoAuth(params);
        String accessToken = kakaoAuthResponse.getAccessToken();

        KakaoUserInfoResponse kakaoUserInfo = kakaoUserInfoClient.kakaoUserInfo("Bearer " + accessToken);

        EssentialUserInfo userInfo = new EssentialUserInfo(
                kakaoUserInfo.getKakaoAccount().getEmail(),
                kakaoUserInfo.getKakaoAccount().getProfile().getThumbnailImageUrl(),
                kakaoUserInfo.getKakaoAccount().getProfile().getNickname(),
                KAKAO
        );

        Integer userId = getUserId(userInfo);

        return getTokenResponse(userId);
    }


    private Integer getUserId(EssentialUserInfo userInfo) {

        String email = userInfo.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        if (optionalUser.isEmpty()) {
            user = saveUser(userInfo);
        } else {
            user = optionalUser.get();
            user.checkProvider(userInfo.getProvider());
        }

        return user.getId();
    }


    private User saveUser(EssentialUserInfo userInfo) {
        return userRepository.save(User.builder()
                .email(userInfo.getEmail())
                .profile(userInfo.getPicture())
                .name(userInfo.getName())
                .provider(userInfo.getProvider())
                .build());
    }


    private TokenResponse getTokenResponse(Integer userId) {
        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        return new TokenResponse(accessToken, refreshToken);
    }

}
