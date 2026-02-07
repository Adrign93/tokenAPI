package com.adrign93.tokenGenerator.controller.docs;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interfaz para documentar los endpoints
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Error en los datos de entrada"),
        @ApiResponse(responseCode = "401", description = "Error al validar el token"),
        @ApiResponse(responseCode = "404", description = "No se ha encontrado el usuario"),
        @ApiResponse(responseCode = "500", description = "Error interno")
})
public @interface TokenDocs {
}
