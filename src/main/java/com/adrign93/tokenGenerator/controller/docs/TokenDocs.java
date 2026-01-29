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
        @ApiResponse(responseCode = "200", description = "Operación realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos de entrada"),
        @ApiResponse(responseCode = "500", description = "Error interno")
})
public @interface TokenDocs {
}
