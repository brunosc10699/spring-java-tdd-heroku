package com.bruno.project.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class MainResource {

    @Value("${heroku.swagger.url}")
    private String swaggerUrl;

    @GetMapping
    public String mainPage(){
        return "<br /><br /><br /><br /><br /><center>" +
                "<h1>A Simple Books Management API</h1>" +
                "<hr />" +
                "Docs <a href=\"" + swaggerUrl + "/swagger-ui.html\">here</a>" +
                "</center>";
    }

}
