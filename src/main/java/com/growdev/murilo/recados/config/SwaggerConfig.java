package com.growdev.murilo.recados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.growdev.murilo.recados.resources"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        // Contact contact = new Contact("webtutsplus", "http://webtutsplus.com", "contact.webtutsplus@gmail.com");
        return new ApiInfoBuilder()
                .title("Recados API")
                .description("Documentation recados api")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apachec.org/licenses/LICENSE-2.0")
                // .contact(contact)
                .build();
    }
}
