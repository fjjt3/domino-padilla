package com.example.hex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI dominoGameOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bar Padilla Tatooine API")
                        .description("Real-time Domino Game API with Star Wars atmosphere")
                        .version("1.0"));
    }
}
