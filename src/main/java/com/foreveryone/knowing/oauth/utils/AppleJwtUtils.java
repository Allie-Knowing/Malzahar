package com.foreveryone.knowing.oauth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreveryone.knowing.oauth.client.apple.AppleAuthClient;
import com.foreveryone.knowing.oauth.dto.response.ApplePublicKeyResponse;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleJwtUtils {
    private final AppleAuthClient appleAuthClient;

    public Claims getClaimsBy(String identityToken) throws NoSuchAlgorithmException, InvalidKeySpecException, JsonProcessingException {

        ApplePublicKeyResponse response = appleAuthClient.appleKeys();

        String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
        Map<String, String> header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8), Map.class);
        System.out.println("header = " + header);
        ApplePublicKeyResponse.Key key = response.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

        byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
        KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();
    }

}
