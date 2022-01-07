package com.foreveryone.knowing.service;

import com.foreveryone.knowing.dto.response.TokenResponse;
import com.foreveryone.knowing.entity.User;
import com.foreveryone.knowing.entity.UserRepository;
import com.foreveryone.knowing.error.exceptions.ProviderDoesNotMatchException;
import com.foreveryone.knowing.security.JwtTokenProvider;
import com.foreveryone.knowing.util.client.GoogleAuthClient;
import com.foreveryone.knowing.util.client.GoogleTokenInfoClient;
import com.foreveryone.knowing.util.dto.request.GoogleAuthRequest;
import com.foreveryone.knowing.util.dto.response.GoogleAuthResponse;
import com.foreveryone.knowing.util.dto.response.GoogleTokenInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value(value = "${oauth.google.client_id}")
    private String GOOGLE_CLIENT_ID;

    @Value(value = "${oauth.google.client_secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value(value = "${oauth.google.redirect_uri}")
    private String GOOGLE_REDIRECT_URI;

    private final UserRepository userRepository;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleTokenInfoClient googleTokenInfoClient;

    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse googleUserInfo(String code) {

        GoogleAuthRequest googleAuthRequest = GoogleAuthRequest.builder()
                .code(URLDecoder.decode(code, StandardCharsets.UTF_8))
                .client_id(GOOGLE_CLIENT_ID)
                .client_secret(GOOGLE_CLIENT_SECRET)
                .redirect_uri(GOOGLE_REDIRECT_URI)
                .grant_type("authorization_code")
                .build();

        GoogleAuthResponse googleAuthResponse = googleAuthClient.googleAuth(googleAuthRequest);

        String id_token = googleAuthResponse.getId_token();
        GoogleTokenInfoResponse tokenInfo = googleTokenInfoClient.getTokenInfo(id_token);

        Integer userId = getUserId(tokenInfo, "FACEBOOK");
        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        return new TokenResponse(accessToken, refreshToken);
    }

    private Integer getUserId(GoogleTokenInfoResponse tokenInfo, String provider) {

        String email = tokenInfo.getEmail();
        String picture = tokenInfo.getPicture();
        String name = tokenInfo.getName();

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
            if (!user.getProvider().equals(provider)) {
                throw new ProviderDoesNotMatchException("이미 다른 방법으로 가입하셨네요? " + user.getProvider() + " 계정으로 로그인해보세요!");
            }
        }

        return user.getId();
    }
}
