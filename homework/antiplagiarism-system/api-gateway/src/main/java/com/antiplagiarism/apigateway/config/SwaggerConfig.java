package com.antiplagiarism.apigateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Antiplagiarism System API",
        version = "1.0",
        description = "API для системы проверки работ на плагиат"
))
public class SwaggerConfig {
}