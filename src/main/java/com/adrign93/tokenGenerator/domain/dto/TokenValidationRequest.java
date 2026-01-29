package com.adrign93.tokenGenerator.domain.dto;

import com.adrign93.tokenGenerator.sanitizer.HtmlSanitizerSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Clase para definir los datos de entrada para generar el token
 */
@JsonDeserialize(using = HtmlSanitizerSerializer.class)
@Data
@AllArgsConstructor
@Builder
@Schema(description = "Modelo que representa los datos de entrada para validar el token")
public class TokenValidationRequest {

    /** Token a validar" */
    @Schema(description = "Token a validar", example = "eyJ1c2VybmFtZSI6dXNlcm5hbWUsImVudGl0eSI6ZW50aXR5fQ.eyJ1c2VybmFtZSI6InVzZXJuYW1lIiwiZW50aXR5IjoiZW50aXR5IiwiaWF0IjoxNzY4OTk2OTIyLCJleHAiOjE3NjkwMDA1MjJ9.miUs7SsKQ55XQdjjvgSDuSZ3jxpDlcs60wzN9X_-N8c")
    private String token;
}
