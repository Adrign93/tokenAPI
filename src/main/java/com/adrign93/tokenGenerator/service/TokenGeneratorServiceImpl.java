package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenValidationResponse;
import com.adrign93.tokenGenerator.domain.entity.User;
import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.utils.GenerateStringsUtils;
import com.adrign93.tokenGenerator.utils.TokenEncodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
     * Valida el token JWT
     * @param token String
     * @return boolean
     */
    @Override
    public TokenValidationResponse validateToken(String token) {
        return TokenValidationResponse.builder()
                .success(isAValidToken(token))
                .build();
    }

    /**
     * Valida el token
     * @param token String
     * @return boolean
     */
    private boolean isAValidToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }
            String encodedHeader = parts[0];
            String encodedPayload = parts[1];
            String signature = parts[2];

            // Decodificar Header
            long exp = getElement("exp", encodedPayload).asLong();
            String username = getElement("username", encodedPayload).asText();

            // Comprobar si ha expirado (exp en segundos desde epoch)
            Instant expirationInstant = Instant.ofEpochSecond(exp);
            Instant now = Instant.now(clock);
            if (expirationInstant.isBefore(now)) {
                return false;
            }

            // Obtener el password del usuario para verificar la firma
            User user = userService.findByUsername(username);
            if (user == null) {
                return false;
            }

            // Recalcular la firma y comparar
            String signatureInput = encodedHeader + "." + encodedPayload;
            String expectedSignature = TokenEncodeUtils.encode(TokenEncodeUtils.hmacSha256(signatureInput, SECRET.concat(user.getPassword())));

            // Si la firma generada es igual a la recibida, el token es válido
            return signature.equals(expectedSignature);

        } catch (Exception e) {
            // Si algo falla (parsing, decodificación, etc.), el token no es válido
            log.error("Error al validar el token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un elemento del payload
     * @param element String
     * @param encodedPayload String
     * @return JsonNode
     * @throws JsonProcessingException Exception
     */
    private JsonNode getElement(String element, String encodedPayload) throws JsonProcessingException {
        // Decodificar Payload para obtener datos
        String payloadJson = new String(Base64.getUrlDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
        JsonNode payloadNode = null;
        payloadNode = objectMapper.readTree(payloadJson);
        return payloadNode.get(element);
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
