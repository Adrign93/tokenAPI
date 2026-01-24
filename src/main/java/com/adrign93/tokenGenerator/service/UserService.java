package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.entity.User;

/**
 * Interfaz para definir los servicios de usuario
 */
public interface UserService {

    User findByUsername(String username);

    void validateUser(TokenRequest tokenRequest);
}
