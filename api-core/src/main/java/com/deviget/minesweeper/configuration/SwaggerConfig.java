package com.deviget.minesweeper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Swagger 2 configuration class
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    /**
     * API info.-
     *
     * @return an API info object
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Minesweeper API",
                "<b>Spring Boot 2 REST API</b> </br>- Java 8 </br>- Lombok </br>- Spring Security + JWT </br>- H2 </br>- JUnit 4 + Mockito + PowerMock + Jacoco </br>- SpringFox + Swagger 2",
                "V1",
                "urn:tos",
                new Contact("Sebastián Kapcitzky", "https://www.linkedin.com/in/kapsebastian", "kap.sebastian@gmail.com"),
                "CC BY-SA 3.0",
                "https://creativecommons.org/licenses/by-sa/3.0/",
                Collections.emptyList());
    }
}