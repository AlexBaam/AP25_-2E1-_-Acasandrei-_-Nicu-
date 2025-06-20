package org.example.lab11_springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    @Bean
    public Docket swaggerSpringfoxDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .apiInfo(ApiInfo.DEFAULT)
                .pathMapping("/")
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(new ApiKey("JWT", AUTHORIZATION_HEADER, "header")))
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
        return docket;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    Contact contact = new Contact(
            "Irimia Cosmin-Iulian",
            "https://profs.info.uaic.ro/cosmin.irimia/",
            "irimia.cosmin@gmail.com");
    ApiInfo apiInfo = new ApiInfo(
            "FII - Cosmin Irimia - Spring API",
            "Description",
            "1.2.3",
            "https://profs.info.uaic.ro/cosmin.irimia/",
            contact,
            "MIT",
            "https://profs.info.uaic.ro/cosmin.irimia/",
            List.of());
}