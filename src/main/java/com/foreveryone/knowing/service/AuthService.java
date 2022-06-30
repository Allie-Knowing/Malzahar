package com.foreveryone.knowing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreveryone.knowing.dto.request.auth.CodeRequest;
import com.foreveryone.knowing.dto.request.auth.GoogleLoginRequest;
import com.foreveryone.knowing.dto.request.auth.IdTokenRequest;
import com.foreveryone.knowing.dto.response.auth.TokenResponse;
import com.foreveryone.knowing.entity.Iq;
import com.foreveryone.knowing.entity.auth.redis.RefreshToken;
import com.foreveryone.knowing.entity.auth.User;
import com.foreveryone.knowing.entity.tier.Tier;
import com.foreveryone.knowing.error.exceptions.NotFoundException;
import com.foreveryone.knowing.error.exceptions.auth.InvalidIdTokenException;
import com.foreveryone.knowing.error.exceptions.auth.InvalidUserTokenException;
import com.foreveryone.knowing.oauth.utils.AppleJwtUtils;
import com.foreveryone.knowing.error.exceptions.auth.InvalidRefreshTokenException;
import com.foreveryone.knowing.oauth.client.google.GoogleAuthClient;
import com.foreveryone.knowing.repository.IqRepository;
import com.foreveryone.knowing.repository.auth.redis.RefreshTokenRepository;
import com.foreveryone.knowing.repository.auth.UserRepository;
import com.foreveryone.knowing.oauth.utils.OauthRequestDtoBuilder;
import com.foreveryone.knowing.oauth.client.facebook.FacebookAuthClient;
import com.foreveryone.knowing.oauth.client.facebook.FacebookUserInfoClient;
import com.foreveryone.knowing.oauth.client.kakao.KakaoAuthClient;
import com.foreveryone.knowing.oauth.client.kakao.KakaoUserInfoClient;
import com.foreveryone.knowing.oauth.dto.request.*;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.facebook.FacebookUserInfoResponse;
import com.foreveryone.knowing.oauth.dto.response.kakao.KakaoAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.kakao.KakaoUserInfoResponse;
import com.foreveryone.knowing.repository.tier.TierCategoryRepository;
import com.foreveryone.knowing.repository.tier.TierRepository;
import com.foreveryone.knowing.security.JwtConfigurationProperties;
import com.foreveryone.knowing.security.JwtTokenProvider;
import com.foreveryone.knowing.oauth.client.google.GoogleUserInfoClient;
import com.foreveryone.knowing.oauth.client.naver.NaverAuthClient;
import com.foreveryone.knowing.oauth.client.naver.NaverUserInfoClient;
import com.foreveryone.knowing.oauth.dto.EssentialUserInfo;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverAuthResponse;
import com.foreveryone.knowing.oauth.dto.response.naver.NaverUserInfoResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

import static com.foreveryone.knowing.oauth.utils.OauthProvider.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final IqRepository iqRepository;
    private final TierRepository tierRepository;
    private final TierCategoryRepository tierCategoryRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final GoogleUserInfoClient googleUserInfoClient;
    private final GoogleAuthClient googleAuthClient;
    private final NaverAuthClient naverAuthClient;
    private final NaverUserInfoClient naverUserInfoClient;
    private final FacebookAuthClient facebookAuthClient;
    private final FacebookUserInfoClient facebookUserInfoClient;
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;

    private final OauthRequestDtoBuilder oauthDtoBuilder;

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtConfigurationProperties jwtConfigurationProperties;

    private final AppleJwtUtils appleJwtUtils;


    public TokenResponse googleLogin(GoogleLoginRequest googleLoginRequest) {

        EssentialUserInfo userInfo = EssentialUserInfo.builder()
                .email(googleLoginRequest.getEmail())
                .picture(googleLoginRequest.getPicture())
                .name(googleLoginRequest.getName())
                .provider(GOOGLE)
                .build();

        return getUserAndReturnToken(userInfo);
    }


    public TokenResponse naverLogin(CodeRequest codeRequest) {

        NaverAuthRequest naverAuthRequest = oauthDtoBuilder.getNaver(codeRequest.getCode());

        NaverAuthResponse naverAuthResponse = naverAuthClient.naverAuth(naverAuthRequest);
        String accessToken = naverAuthResponse.getAccessToken();

        NaverUserInfoResponse userInfoResponse = naverUserInfoClient.naverUserInfo("Bearer " + accessToken);
        NaverUserInfoResponse.Response response = userInfoResponse.getResponse();

        EssentialUserInfo userInfo = EssentialUserInfo.builder()
                .email(response.getEmail())
                .picture(response.getProfileImage())
                .name(response.getName())
                .provider(NAVER)
                .build();

        System.out.println("NAVER 로그인 성공");

        return getUserAndReturnToken(userInfo);
    }


    public TokenResponse facebookLogin(CodeRequest codeRequest) {
        String code = codeRequest.getCode();
        String redirectUri = codeRequest.getRedirectUri();

        FacebookAuthRequest facebookAuthRequest = oauthDtoBuilder.getFacebook(code, redirectUri);

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

        System.out.println("FACEBOOK 로그인 성공");

        return getUserAndReturnToken(userInfo);
    }

    public TokenResponse kakaoLogin(CodeRequest codeRequest) {
        String code = codeRequest.getCode();
        String redirectUri = codeRequest.getRedirectUri();

        KakaoAuthRequest kakaoAuthRequest = oauthDtoBuilder.getKakao(code, redirectUri);

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

        System.out.println("KAKAO 로그인 성공");

        return getUserAndReturnToken(userInfo);
    }

    public TokenResponse appleLogin(IdTokenRequest idTokenRequest) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {

        Claims claims = appleJwtUtils.getClaimsBy(idTokenRequest.getIdToken());

        String email = claims.get("email", String.class);
        if (email == null ) {
            throw new InvalidIdTokenException("잘못된 ID token 입니다.");
        }
        EssentialUserInfo userInfo = EssentialUserInfo.builder()
                .email(email)
                .name(idTokenRequest.getName() == null ? email.split("@")[0] : idTokenRequest.getName())
                .provider(APPLE)
                .build();

        System.out.println("APPLE 로그인 성공");

        return getUserAndReturnToken(userInfo);
    }
  
    public TokenResponse tokenRefresh(String refreshToken) {

        isRefreshToken(refreshToken);

        doesTokenExist(refreshToken);

        Integer id = jwtTokenProvider.getId(refreshToken);

        System.out.println("토큰 리프레시 성공");

        return getTokenResponse(id, null);
    }

    private void doesTokenExist(String refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);

        if(optionalRefreshToken.isEmpty())
            throw new InvalidRefreshTokenException("저장된 리프레시 토큰 없음");

        refreshTokenRepository.delete(optionalRefreshToken.get());
    }

    private void isRefreshToken(String refreshToken) {
        if(!jwtTokenProvider.isRefreshToken(refreshToken))
            throw new InvalidRefreshTokenException("리프레시 토큰이 아닙니다.");
    }


    private TokenResponse getUserAndReturnToken(EssentialUserInfo userInfo) {

        String email = userInfo.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user;

        Boolean isFirstLogin = optionalUser.isEmpty();

        if (isFirstLogin) {
            user = saveUser(userInfo);
        } else {
            user = optionalUser.get();
            user.checkProvider(userInfo.getProvider());
        }

        if (user.getDeletedAt() != null) {
            throw new InvalidUserTokenException("탈퇴한 유저입니다.");
        }

        return getTokenResponse(user.getId(), isFirstLogin);
    }

    @Transactional
    private User saveUser(EssentialUserInfo userInfo) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = userRepository.save(User.builder()
                .email(userInfo.getEmail())
                .profile(userInfo.getPicture())
                .name(userInfo.getName())
                .provider(userInfo.getProvider())
                .createdAt(now)
                .updatedAt(now)
                .lastAccessedAt(now)
                .build());

        iqRepository.save(Iq.builder()
                .user(user)
                .curCnt(BigInteger.valueOf(0))
                .totCnt(BigInteger.valueOf(0))
                .build());

        tierRepository.save(Tier.builder()
                .updatedAt(now)
                .user(user)
                .tierCategory(tierCategoryRepository.findById(1)
                        .orElseThrow(() -> new NotFoundException("티어 카테고리를 찾을 수 없음")))
                .build());

        return user;
    }


    private TokenResponse getTokenResponse(Integer userId, Boolean isFirstLogin) {
        String accessToken = jwtTokenProvider.generateAccessToken(userId);

        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(jwtTokenProvider.generateRefreshToken(userId))
                .exp(jwtConfigurationProperties.getExp().getRefresh())
                .build()
        );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .isFirstLogin(isFirstLogin)
                .build();
    }

}
