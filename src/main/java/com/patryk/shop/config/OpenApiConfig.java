package com.patryk.shop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@Profile("!prod")
public class OpenApiConfig {
    static {
        SpringDocUtils.getConfig().replaceWithClass(org.springframework.data.domain.Pageable.class,
                org.springdoc.core.converters.models.Pageable.class);
        SpringDocUtils.getConfig().replaceWithClass(LocalTime.class,
                String.class);
        SpringDocUtils.getConfig().replaceWithClass(LocalDateTime.class,
                String.class);
        SpringDocUtils.getConfig().replaceWithClass(LocalDate.class,
                String.class);
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .packagesToScan("com.patryk.shop.controller")
                .group("default")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecuritySchemes("BasicAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                        .title("Shop.Api")
                        .version("1.0.0")
                        .description("This is a sample server Petstore server. You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). For this sample, you can use the api key special-key to test the authorization filters.")
                        .termsOfService("")
                        .license(new License()
                                .name("")
                                .url("")));
    }

}
