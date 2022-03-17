package com.foreveryone.knowing.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.*;
import static org.springframework.util.StreamUtils.*;

public class GoogleMocks {

    public static void setUpMockGoogleAuthResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/token"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/google-auth-response.json"),
                                        defaultCharset()))));
    }

    public static void setUpMockGoogleUserInfoResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/tokenInfo?id_token=idToken"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/google-userInfo-response.json"),
                                        defaultCharset()))));
    }
}
