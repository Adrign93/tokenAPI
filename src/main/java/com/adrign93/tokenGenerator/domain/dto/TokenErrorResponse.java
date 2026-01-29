package com.adrign93.tokenGenerator.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenErrorResponse {
    /** error code */
    @Schema(description = "Código de error", example = "400")
    private String errorCode;

    /** Mensaje de error */
    @Schema(description = "Mensaje de error", example = "Error en los datos de entrada")
    private String message;
}
