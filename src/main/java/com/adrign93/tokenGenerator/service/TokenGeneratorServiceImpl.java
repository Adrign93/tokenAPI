package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenValidationResponse;
import com.adrign93.tokenGenerator.domain.entity.User;
import com.adrign93.tokenGenerator.exception.ExpirationException;
import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.exception.ResourceNotFoundException;
import com.adrign93.tokenGenerator.exception.TokenFormatException;
import com.adrign93.tokenGenerator.utils.GenerateStringsUtils;
import com.adrign93.tokenGenerator.utils.TokenEncodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/**
 * Este servicio implementa la interfaz TokenGeneratorService para generar el token JWT
 */
@Slf4j
@Service
public class TokenGeneratorServiceImpl implements TokenGeneratorService {

    private final String SECRET;
    private final Integer EXPIRATION_TIME;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Clock clock;


    /**
     * Constructor de la clase
     * @param secret String
     * @param expiration Integer
     */
    @Autowired
    public TokenGeneratorServiceImpl(@Value("${jwt.secret}") String secret,
                                     @Value("${jwt.expiration}") Integer expiration,
                                     UserService userService) {
        this(secret, expiration, userService, Clock.systemUTC());
    }

    /**
     * Constructor para tests: permite controlar "ahora" mediante Clock
     */
    TokenGeneratorServiceImpl(String secret,
                              Integer expiration,
                              UserService userService,
                              Clock clock) {
        this.SECRET = secret;
        this.EXPIRATION_TIME = expiration;
        this.userService = userService;
        this.clock = clock;
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

    /**
     * Valida el token
     * @param token String
     */
    @Override
    public void isAValidToken(String token) {
        String[] parts = token.split("\\.");
        isAValidFormatToke(parts);
        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String signatureRequest = parts[2];

        // Decodificar Header
        long tokenExpirationTime = TokenEncodeUtils.getElement("exp", encodedPayload).asLong();
        String tokenUsername = TokenEncodeUtils.getElement("username", encodedPayload).asText();

        // Valida si el token ha expirado
        isTokenInTime(clock, tokenExpirationTime);

        // Obtener el password del usuario para verificar la firma
        User userBBDD = userService.findByUsername(tokenUsername);

        // Si la firma generada es igual a la recibida, el token es válido
        isACorrectSignature(signatureRequest, generateSignature(encodedHeader, encodedPayload, userBBDD));
    }

    /**
     * Valida si el formato del token es correcto
     * @param tokenParts String[]
     */
    private void isAValidFormatToke(String[] tokenParts) {
        if (tokenParts.length != 3) {
            throw new TokenFormatException("El token no tiene el formato correcto");
        }
    }

    /**
     * Valida si el token ha expirado
     * @param clock Clock
     * @param tokenExpirationTime long
     * @throws ExpirationException Exception
     */
    private void isTokenInTime(Clock clock, long tokenExpirationTime) throws ExpirationException {
        // Comprobar si ha expirado (exp en segundos desde epoch)
        Instant expirationInstant = Instant.ofEpochSecond(tokenExpirationTime);
        Instant now = Instant.now(clock);
        if (expirationInstant.isBefore(now)) {
            throw new ExpirationException("El token ha expirado");
        }
    }

    private String generateSignature(String encodedHeader, String encodedPayload, User userBBDD) {
        // Recalcular la firma y comparar
        String signatureInput = encodedHeader + "." + encodedPayload;
        return TokenEncodeUtils.encode(TokenEncodeUtils.hmacSha256(signatureInput,
                SECRET.concat(userBBDD.getPassword())));
    }

    /**
     * Comprueba si la firma del token es correcta
     * @param requestSignature String
     * @param expectedSignature String
     */
    private void isACorrectSignature(String requestSignature, String expectedSignature) {
        if(!requestSignature.equals(expectedSignature)){
            throw new TokenFormatException("La firma del token no es correcta");
        }
    }
}
