package com.deviget.minesweeper.configuration;

import com.google.common.base.Predicates;
import org.hibernate.cfg.beanvalidation.BeanValidationIntegrator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger 2 configuration class
 */
@Configuration
@EnableSwagger2
@Import(BeanValidationIntegrator.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//<6>, regex must be in double quotes.
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
                "Spring Boot 2 REST API - Java 8 - Lombok - H2 - JUnit 4 + Mockito + PowerMock - SpringFox + Swagger 2",
                "V1",
                "urn:tos",
                new Contact("Sebasián Kapcitzky", "https://www.linkedin.com/in/kapsebastian", "kap.sebastian@gmail.com"),
                "CC BY-SA 3.0",
                "https://creativecommons.org/licenses/by-sa/3.0/",
                Collections.emptyList()
        );
    }
}