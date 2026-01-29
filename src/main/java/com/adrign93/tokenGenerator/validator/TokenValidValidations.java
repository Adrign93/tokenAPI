package com.adrign93.tokenGenerator.validator;

import com.adrign93.tokenGenerator.domain.dto.TokenValidationRequest;

import java.util.List;
import java.util.Objects;

/**
 * Clase para validar los datos de entrada del TokenValidationRequest
 */
public class TokenValidValidations {

    /**
     * Constructor privado para evitar instanciar la clase
     */
    private TokenValidValidations(){}

    /**
     * Validaciones para los datos de entrada
     */
    private final static Validator<TokenValidationRequest, RuntimeException> isNull = request -> {
        if(Objects.isNull(request)) throw new IllegalArgumentException("El token no puede ser nulo");
    };

    /**
     * Lista de validaciones
     */
    public static List<Validator<TokenValidationRequest, RuntimeException> > rules = List.of(isNull);
}
