package com.foreveryone.knowing.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class FacebookMocks {

    public static void setUpMockFacebookAuthResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v12.0/oauth/access_token?code=code&client_secret=client_secret&redirect_uri=redirect_uri&client_id=client_id"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/facebook-auth-response.json"),
                                        defaultCharset()))));
    }

    public static void setUpMockFacebookUserInfoResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/me?access_token=access_token&fields=name%2Cemail%2Cpicture"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GoogleMocks.class.getClassLoader().getResourceAsStream("payload/facebook-userInfo-response.json"),
                                        defaultCharset()))));
    }
}
