package com.francis.checkout.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.francis.checkout.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo()).directModelSubstitute(Timestamp.class, Long.class);
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title("Checkout System API")
                .description("This API allows the supermarket user to add, update, read and delete items in a basket, as well as calculates the total cost of a basket which could contain any combination of items and promotions.")
                .version("0.0.1")
                .build();
    }
}
