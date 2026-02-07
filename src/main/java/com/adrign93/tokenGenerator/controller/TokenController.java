package com.adrign93.tokenGenerator.controller;

import com.adrign93.tokenGenerator.controller.docs.TokenDocs;
import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.domain.dto.TokenValidationRequest;
import com.adrign93.tokenGenerator.domain.dto.TokenValidationResponse;
import com.adrign93.tokenGenerator.service.TokenGeneratorService;
import com.adrign93.tokenGenerator.service.UserService;
import com.adrign93.tokenGenerator.validator.TokenGenerationValidations;
import com.adrign93.tokenGenerator.validator.TokenValidValidations;
import com.adrign93.tokenGenerator.validator.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador para generar y validar el token
 */
@RestController
@RequestMapping("/api/v1/token")
@Tag(name = "token", description = "Operaciones relacionadas con la generación y la validación del token")
public class TokenController {

    private final TokenGeneratorService tokenService;

    private final UserService userService;

    /** Hacemos la inyección de dependencias vía constructor*/
    public TokenController(TokenGeneratorService tokenService,
                           UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    /**
     * Api encargada de generar el token a partir de los datos de entrada
     * @param tokenRequest TokenRequest
     * @return Token generado
     */
    @Operation(summary = "Obtiene el token de un usuario", description = "Obtiene el token generado de un usuario ")
    @TokenDocs
    @ApiResponse(responseCode = "200", description = "Token generado correctamente")
    @PostMapping
    public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest) {
        // Validamos los datos de entrada
        Validator.applyRules(TokenGenerationValidations.rules, tokenRequest);

        // Validamos que exista el usuario en la base de datos
        this.userService.validateUser(tokenRequest);

        return ResponseEntity.ok(tokenService.generateToken(tokenRequest));
    }

    /**
     * Api encargada de validar el token
     * @param tokenRequest TokenValidationRequest
     * @return TokenValidationResponse
     */
    @Operation(summary = "Valida el token de un usuario", description = "Valida el token generado de un usuario ")
    @TokenDocs
    @ApiResponse(responseCode = "202", description = "Token validado correctamente")
    @PostMapping(path = "/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestBody TokenValidationRequest tokenRequest) {

        // Validamos los datos de entrada
        Validator.applyRules(TokenValidValidations.rules, tokenRequest);

        // Validamos el token
        this.tokenService.isAValidToken(tokenRequest.getToken());

        return ResponseEntity.noContent().build();
    }
}
