package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.utils.GenerateStringsUtils;
import com.adrign93.tokenGenerator.utils.TokenEncodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Este servicio implementa la interfaz TokenGeneratorService para generar el token JWT
 */
@Service
public class TokenGeneratorServiceImpl implements TokenGeneratorService {

    private final String SECRET;
    private final Integer EXPIRATION_TIME;

    /**
     * Constructor de la clase
     * @param secret String
     * @param expiration Integer
     */
    public TokenGeneratorServiceImpl(@Value("${jwt.secret}") String secret,
                                     @Value("${jwt.expiration}") Integer expiration) {
        this.SECRET = secret;
        this.EXPIRATION_TIME = expiration;
    }

    /**
     * Genera el token JWT a partir de los datos de entrada
     * @param tokenRequest TokenRequest
     * @return TokenResponse
     */
    @Override
    public TokenResponse generateToken(TokenRequest tokenRequest){

        // Crear la Firma (Signature)
        String signatureInput = generateSignatureInput(tokenRequest, EXPIRATION_TIME);
        String signature;
        try {
            signature = TokenEncodeUtils.encode(TokenEncodeUtils.hmacSha256(signatureInput, SECRET.concat(tokenRequest.getPassword())));
        } catch (Exception e) {
            throw new InternalServerException("Se ha producido un error al generar el token");
        }

        return TokenResponse.builder()
                .token(signatureInput + "." + signature)
                .success(true)
                .build();
    }

    /**
     * Genera la cadena de entrada para la firma del token JWT
     * @param tokenRequest TokenRequest
     * @param expirationTime Integer
     * @return String
     */
    private String generateSignatureInput(TokenRequest tokenRequest, Integer expirationTime ){
        // 1. Crear Header (Algoritmo y Tipo)
        String header = GenerateStringsUtils.generateHeader(tokenRequest);

        // 2. Crear Payload (Datos y Expiración)
        String payload = GenerateStringsUtils.generatePayload(tokenRequest, expirationTime);

        // 3. Codificar en Base64URL
        String encodedHeader = TokenEncodeUtils.encode(header.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = TokenEncodeUtils.encode(payload.getBytes(StandardCharsets.UTF_8));
        return encodedHeader + "." + encodedPayload;
    }
}
