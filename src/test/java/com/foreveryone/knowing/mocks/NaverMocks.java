package com.foreveryone.knowing.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class NaverMocks {

    public static void setUpMockNaverAuthResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/token?code=code&grant_type=authorization_code&client_secret=client_secret&client_id=client_id"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/naver-auth-response.json"),
                                        defaultCharset()))));
    }

    public static void setUpMockNaverUserInfoResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/nid/me"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/naver-userInfo-response.json"),
                                        defaultCharset()))));
    }
}
