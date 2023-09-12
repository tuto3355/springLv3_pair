package com.nakta.springlv1.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "SpringLv5 API 명세서",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "ㅋㅋ",
                        email = "Merong@email.co.kr"
                )
        )
)
//todo: SecuritySchemes
@Configuration
public class SwaggerConfig {

//
//    @Bean
//    public GroupedOpenApi sampleGroupOpenApi() {
//        String[] paths = {"/member/**"};
//
//        return GroupedOpenApi.builder().group("샘플 멤버 API").pathsToMatch(paths)
//            .build();
//    }

}
