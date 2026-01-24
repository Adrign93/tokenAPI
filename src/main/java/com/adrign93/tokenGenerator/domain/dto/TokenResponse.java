package com.adrign93.tokenGenerator.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Clase para definir los datos de salida del token
 */
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {
    /** Token generado */
    @Schema(description = "Token generado", example = "eyJ1c2VybmFtZSI6dXNlcm5hbWUsImVudGl0eSI6ZW50aXR5fQ.eyJ1c2VybmFtZSI6InVzZXJuYW1lIiwiZW50aXR5IjoiZW50aXR5IiwiaWF0IjoxNzY4OTk2OTIyLCJleHAiOjE3NjkwMDA1MjJ9.miUs7SsKQ55XQdjjvgSDuSZ3jxpDlcs60wzN9X_-N8c")
    private String token;

    /** Generado correctamente */
    @Schema(description = "Generado correctamente", example = "true")
    private boolean success;

    /** error code */
    @Schema(description = "Código de error", example = "400")
    private String errorCode;

    /** Mensaje de error */
    @Schema(description = "Mensaje de error", example = "Error en los datos de entrada")
    private String message;
}
