package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.dto.TokenRequest;

/**
 * Interfaz para generar el token
 */
public interface TokenGeneratorService {

    /** Genera el token */
    TokenResponse generateToken(TokenRequest tokenRequest);
}
