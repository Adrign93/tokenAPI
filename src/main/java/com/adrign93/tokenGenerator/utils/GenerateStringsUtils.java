package com.adrign93.tokenGenerator.utils;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;

public class GenerateStringsUtils {

    /**
     * Genera el payload del token JWT
     * @param tokenRequest TokenRequest
     * @return String
     */
    public static String generatePayload(TokenRequest tokenRequest, Integer expirationTime){
        long now = System.currentTimeMillis() / 1000;
        return "{" +
                "\"username\":\"" + tokenRequest.getUsername() + "\"," +
                "\"entity\":\"" + tokenRequest.getEntity() + "\"," +
                "\"iat\":" + now + "," +
                "\"exp\":" + (now + expirationTime) +
                "}";
    }

    /**
     * Genera el header del token JWT
     * @param tokenRequest TokenRequest
     * @return String
     */
    public static String generateHeader(TokenRequest tokenRequest) {
        return "{\"username\":"+ tokenRequest.getUsername() +",\"entity\":"+ tokenRequest.getEntity() +"}";
    }
}
