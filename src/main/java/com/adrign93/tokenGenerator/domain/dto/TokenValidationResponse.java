package com.adrign93.tokenGenerator.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Dto de respuesta para la validación del token
 */
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenValidationResponse {

    /** Generado correctamente */
    @Schema(description = "Generado correctamente", example = "true")
    private boolean success;

    /** Error response */
    private TokenErrorResponse error;
}
