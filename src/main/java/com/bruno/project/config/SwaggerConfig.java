package com.bruno.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    private final ResponseMessage message201 = customMessage();
    private final ResponseMessage message204put = simpleMessage(204, "Data updated");
    private final ResponseMessage message204delete = simpleMessage(204, "Data deleted");
    private final ResponseMessage message400 = simpleMessage(400, "Bad request");
    private final ResponseMessage message403 = simpleMessage(403, "Forbidden");
    private final ResponseMessage message404 = simpleMessage(404, "Object not found");
    private final ResponseMessage message405 = simpleMessage(405, "Method not allowed");
    private final ResponseMessage message500 = simpleMessage(500, "Unexpected error");

    private ResponseMessage customMessage(){
        Map<String, Header> map = new HashMap<>();
        map.put("location", new Header(
                "location",
                "New resource URI",
                new ModelRef("string"))
        );
        return new ResponseMessageBuilder()
                .code(201)
                .message("Resource created")
                .headersWithDescription(map)
                .build();
    }

    private ResponseMessage simpleMessage(int code, String message){
        return new ResponseMessageBuilder().code(code).message(message).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Simple Books Management API",
                "This API was created for Digital Innovation One's bootcamp",
                "Version 1.0",
                null,
                new Contact("Bruno", "https://github.com/brunosc10699", "brunosc10699@gmail.com"),
                "This API is only allowed for studies",
                null,
                Collections.emptyList()
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(message400, message403, message404, message405, message500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(message201, message403, message400, message404, message405, message500))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(message204put, message403, message400, message404, message405, message500))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(message204delete, message400, message403, message404, message405, message500))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bruno.project.resources"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
