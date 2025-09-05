package com.lifestyle.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI productOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lifestyle Product API")
                        .description("API documentation for Product, Category, Vendor, and Image management")
                        .version("1.0.0"));
    }
}
