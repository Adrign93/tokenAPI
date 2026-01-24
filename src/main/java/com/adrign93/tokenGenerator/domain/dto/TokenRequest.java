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
@Schema(description = "Modelo que representa los datos de entrada para generar el token")
public class TokenRequest {

    /** El username con el que se genera el token */
    @Schema(description = "El username con el que se genera el token", example = "admin")
    private String username;

    /** La password con la que se genera el token */
    @Schema(description = "La password con la que se genera el token", example = "12345")
    private String password;

    /** Entidad/departamento que genera el token */
    @Schema(description = "Entidad/departamento que genera el token", example = "ADM")
    private String entity;
}
