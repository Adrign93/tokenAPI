package com.adrign93.tokenGenerator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  Clase de configuración de OpenApi
 */
@Configuration
public class OpenApiConfig {
    /**
     * Configura el open API
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Mi Proyecto")
                        .version("1.0")
                        .description("Documentación detallada de los servicios REST")
                );
    }
}
