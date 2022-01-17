package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.entity.UserRepository;
import com.foreveryone.knowing.oauth.OauthRequestDtoBuilder;
import com.foreveryone.knowing.oauth.client.facebook.FacebookAuthClient;
import com.foreveryone.knowing.oauth.client.facebook.FacebookUserInfoClient;
import com.foreveryone.knowing.oauth.dto.request.FacebookAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.FacebookUserInfoRequest;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookUserInfoResponse;
import com.foreveryone.knowing.security.JwtTokenProvider;
import com.foreveryone.knowing.oauth.client.google.GoogleAuthClient;
import com.foreveryone.knowing.oauth.client.google.GoogleUserInfoClient;
import com.foreveryone.knowing.oauth.client.naver.NaverAuthClient;
import com.foreveryone.knowing.oauth.client.naver.NaverUserInfoClient;
import com.foreveryone.knowing.oauth.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.oauth.dto.request.NaverAuthRequest;
import com.foreveryone.knowing.oauth.dto.EssentialUserInfo;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.google.GoogleUserInfoResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
