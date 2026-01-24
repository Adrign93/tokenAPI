package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adrign93.tokenGenerator.utils.TestUtils.generateTokenRequest;

@ExtendWith(MockitoExtension.class)
class TokenGeneratorServiceImplTest {

    private TokenGeneratorServiceImpl tokenGeneratorService;

    @BeforeEach
    void setUp() {
        // Simplemente pasas los valores como Strings/Integers normales
        String secretDePrueba = "clave_manual_test";
        Integer expirationDePrueba = 3600;

        tokenGeneratorService = new TokenGeneratorServiceImpl(secretDePrueba, expirationDePrueba);
    }

    @Test
    void testGenerateToken() throws BadRequestException {
        TokenResponse response = tokenGeneratorService.generateToken(generateTokenRequest());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getToken());
        Assertions.assertFalse(response.getToken().isEmpty());
    }

}