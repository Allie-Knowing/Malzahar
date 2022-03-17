package com.foreveryone.knowing.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class KakaoMocks {

    public static void setUpMockKakaoAuthResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/oauth/token"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/kakao-auth-response.json"),
                                        defaultCharset()))));
    }

    public static void setUpMockKakaoUserInfoResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v2/user/me"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/kakao-userInfo-response.json"),
                                        defaultCharset()))));
    }
}
