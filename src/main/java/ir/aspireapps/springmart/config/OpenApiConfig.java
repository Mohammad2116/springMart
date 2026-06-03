package ir.aspireapps.springmart.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springMartOpenAPI() {

    return new OpenAPI()
            .info(new Info()
                    .title("Spring Mart API")
                    .version("1.0")
                    .description("""
                                SpringMart E-Commerce REST API

                                Features:
                                - JWT Authentication
                                - Product Management
                                - Category Management
                                - Shopping Cart
                                - Order Processing
                                - User Management
                                """))

            .components(
                new Components()
                        .addSecuritySchemes(
                                "bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
            );
    }
}