package com.ejemplo.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API Demo - Programación III")
                        .version("1.0.0")
                        .description("API de ejemplo con validaciones, manejo de errores, pruebas y documentación OpenAPI.")
                        .contact(new Contact()
                                .name("Aranza Rueda")
                                .email("aruedaa@miumg.edu.gt")));
    }
}