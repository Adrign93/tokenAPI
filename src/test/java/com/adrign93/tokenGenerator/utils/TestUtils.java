package com.adrign93.tokenGenerator.utils;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;

import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static TokenRequest generateTokenRequest(){
        return TokenRequest.builder()
                .username("username")
                .password("password")
                .entity("entity")
                .build();
    }


    public static String buildToken(String headerJson,
                                    String payloadJson,
                                    String userPassword,
                                    boolean includeSignature) {
        String encodedHeader = TokenEncodeUtils.encode(headerJson.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = TokenEncodeUtils.encode(payloadJson.getBytes(StandardCharsets.UTF_8));
        String secretDePrueba = "clave_manual_test";

        String signatureInput = encodedHeader + "." + encodedPayload;

        if (!includeSignature) {
            return signatureInput;
        }

        String signature = TokenEncodeUtils.encode(
                TokenEncodeUtils.hmacSha256(signatureInput, secretDePrueba.concat(userPassword))
        );

        return signatureInput + "." + signature;
    }

    public static String buildSignedToken(String username, long expSeconds, String userPassword){
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"username\":\"" + username + "\",\"exp\":" + expSeconds + "}";
        return buildToken(headerJson, payloadJson, userPassword, true);
    }
}
