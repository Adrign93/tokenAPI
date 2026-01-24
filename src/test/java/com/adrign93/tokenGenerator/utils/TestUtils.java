package com.adrign93.tokenGenerator.utils;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;

public class TestUtils {

    public static TokenRequest generateTokenRequest(){
        return TokenRequest.builder()
                .username("username")
                .password("password")
                .entity("entity")
                .build();
    }
}
