package com.adrign93.tokenGenerator.controller;


import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.dto.TokenValidationRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenValidationResponse;
import com.adrign93.tokenGenerator.service.TokenGeneratorService;
import com.adrign93.tokenGenerator.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.adrign93.tokenGenerator.utils.TestUtils.generateTokenRequest;


@ExtendWith(MockitoExtension.class)
class TokenControllerTest {

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private TokenGeneratorService tokenService;
    @Mock
    private UserService userService;

    @Test
    void testGenerateToken() {

        ResponseEntity<TokenResponse> result = ResponseEntity.ok(TokenResponse.builder().token("token").build());

        Mockito.when(tokenService.generateToken(Mockito.any(TokenRequest.class))).thenReturn(result.getBody());

        ResponseEntity<TokenResponse> response = tokenController.generateToken(generateTokenRequest());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getToken());
        Assertions.assertFalse(response.getBody().getToken().isEmpty());
        Assertions.assertNotEquals(0, response.getBody().getToken().length());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }


    @Test
    void testValidateToken() {
        ResponseEntity<TokenValidationResponse> result = ResponseEntity.ok(TokenValidationResponse.builder().success(true).build());

        Mockito.when(tokenService.validateToken(Mockito.anyString())).thenReturn(result.getBody());

        ResponseEntity<TokenValidationResponse> response = tokenController.validateToken(TokenValidationRequest.builder().token("token").build());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().isSuccess());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}