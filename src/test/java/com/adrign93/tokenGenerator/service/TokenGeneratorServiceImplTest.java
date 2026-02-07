package com.adrign93.tokenGenerator.service;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.entity.User;
import com.adrign93.tokenGenerator.exception.ExpirationException;
import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.exception.TokenFormatException;
import com.adrign93.tokenGenerator.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static com.adrign93.tokenGenerator.utils.TestUtils.generateTokenRequest;

@ExtendWith(MockitoExtension.class)
class TokenGeneratorServiceImplTest {

    private TokenGeneratorServiceImpl tokenGeneratorService;

    @Mock
    private UserService userService;

    private String secretDePrueba;
    private Integer expirationDePrueba;
    private  Clock clock ;

    @BeforeEach
    void setUp() {
        // Pasamos los valores como Strings/Integers normales
        secretDePrueba = "clave_manual_test";
        expirationDePrueba = 3600;
        clock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
        tokenGeneratorService = new TokenGeneratorServiceImpl(secretDePrueba, expirationDePrueba, userService, clock);
    }

    @Test
    void testGenerateToken() {
        tokenGeneratorService = new TokenGeneratorServiceImpl(secretDePrueba, expirationDePrueba, userService);
        TokenResponse response = tokenGeneratorService.generateToken(generateTokenRequest());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getToken());
        Assertions.assertFalse(response.getToken().isEmpty());
    }

    @Test
    void testGenerateTokenFailureWhenPasswordNull() {
        TokenRequest request = TokenRequest.builder()
                .username("test_user")
                .password(null)
                .entity("ADM")
                .build();

        Assertions.assertThrows(InternalServerException.class,
                () -> tokenGeneratorService.generateToken(request));
    }

    @Test
    void testGenerateTokenFailureInternalException() {
        TokenRequest request = Mockito.mock(TokenRequest.class);

        // Simulate failure during request processing
        Mockito.doThrow(new InternalServerException("Internal error"))
                .when(request).getPassword();

        Assertions.assertThrows(InternalServerException.class,
                () -> tokenGeneratorService.generateToken(request));
    }

    @Test
    void testValidateTokenExpirationException() throws Exception {
        String username = "username";
        String password = "password";

        long expSeconds = clock.instant().minusSeconds(1).getEpochSecond();
        String token = TestUtils.buildSignedToken(username, expSeconds, password);

        Assertions.assertThrows(ExpirationException.class, () -> tokenGeneratorService.isAValidToken(token));
    }

    @Test
    void testValidateTokenExpirationOk() throws Exception {
        String username = "username";
        String password = "password";
        Mockito.when(userService.findByUsername(Mockito.anyString()))
                .thenReturn(User.builder().username(username).password(password).build());

        long expSeconds = clock.instant().plusSeconds(expirationDePrueba).getEpochSecond();
        String token = TestUtils.buildSignedToken(username, expSeconds, password);

        tokenGeneratorService.isAValidToken(token);

        Mockito.verify(userService, Mockito.times(1)).findByUsername(username);
    }

    @Test
    void testValidateTokenUserException() throws Exception {
        String username = "username";
        String password = "password";

        long expSeconds = clock.instant().plusSeconds(expirationDePrueba).getEpochSecond();
        String token = buildInvalidToken(username, expSeconds, password);

        Assertions.assertThrows(TokenFormatException.class, () -> tokenGeneratorService.isAValidToken(token));
    }

    @Test
    void testValidateTokenUserPartsException() throws Exception {
        String username = "username";
        String password = "password";

        long expSeconds = clock.instant().plusSeconds(expirationDePrueba).getEpochSecond();
        String token = buildInvalidSignature(username, expSeconds, password);

        Assertions.assertThrows(TokenFormatException.class, () -> tokenGeneratorService.isAValidToken(token));
    }

    @Test
    void testValidateSignatureException() throws Exception {
        String username = "usernameException";
        String password = "passwordExpception";
        Mockito.when(userService.findByUsername(Mockito.anyString()))
                .thenReturn(User.builder().username(username).password(password).build());

        long expSeconds = clock.instant().plusSeconds(expirationDePrueba).getEpochSecond();
        String token = TestUtils.buildSignedToken("username", expSeconds, "password");

        Assertions.assertThrows(TokenFormatException.class, () -> tokenGeneratorService.isAValidToken(token));
    }

    private String buildInvalidToken(String username, long expSeconds, String userPassword) throws Exception {
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = username + "\",\"exp\":" + expSeconds;
        return TestUtils.buildToken(headerJson, payloadJson, userPassword, true);
    }

    private String buildInvalidSignature(String username, long expSeconds, String userPassword) throws Exception {
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"username\":\"" + username + "\",\"exp\":" + expSeconds + "}";
        return TestUtils.buildToken(headerJson, payloadJson, userPassword, false);
    }

}