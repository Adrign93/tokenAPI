package com.adrign93.tokenGenerator.transform;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.entity.User;

/**
 * Mapper de token a usuario
 */
public class TokenToUser {

    /**
     * Mapea el token a un usuario
     * @param tokenRequest TokenRequest
     * @return User
     */
    public static User mapTokenToUser(TokenRequest tokenRequest) {
        return User.builder()
                .username(tokenRequest.getUsername())
                .password(tokenRequest.getPassword())
                .entity(tokenRequest.getEntity())
                .build();
    }
}
