package com.adrign93.tokenGenerator.validator;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;

import java.util.List;
import java.util.Objects;

/**
 * Clase para validar los datos de entrada
 */
public class TokenGenerationValidations {

    /**
     * Constructor privado para evitar instanciar la clase
     */
    private TokenGenerationValidations(){}

    /**
     * Validaciones para los datos de entrada
     */
    private final static Validator<TokenRequest, RuntimeException> isNull = request -> {
        if(Objects.isNull(request)) throw new IllegalArgumentException("Los parametros no pueden ser nulos");
    };

    /**
     * Validamos el username
     */
    private final static Validator<TokenRequest, RuntimeException> isUsernameEmpty = request -> {
        if(request.getUsername() == null || request.getUsername().isEmpty()) throw new IllegalArgumentException("El username no puede estar vacio");
    };

    /**
     * Validamos la contraseña
     */
    private final static Validator<TokenRequest, RuntimeException> isPasswordEmpty = request -> {
        if(request.getPassword() == null || request.getPassword().isEmpty()) throw new IllegalArgumentException("La contraseña no puede estar vacia");
    };

    /**
     * Validamos la entidad
     */
    private final static Validator<TokenRequest, RuntimeException> isEntityEmpty = request -> {
        if(request.getEntity() == null || request.getEntity().isEmpty()) throw new IllegalArgumentException("La entity no puede estar vacia");
    };

    /**
     * Lista de validaciones
     */
    public static List<Validator<TokenRequest, RuntimeException> >rules = List.of(
            isNull,
            isUsernameEmpty,
            isPasswordEmpty,
            isEntityEmpty);

}
