package com.ryanalexander.minipro.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private ApiInfo getApiInfo(){
        Contact contact = new Contact("阮菜鸡", "http://sayhitotheworld.ryanalexander.cn/", "1162179851@qq.com");
        return new ApiInfo(
                "Ryan Document",
                "但行好事，莫问前程",
                "v1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
    @Bean
    public Docket docket_1(Environment environment){

        Profiles profiles = Profiles.of("dev");
        boolean b = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(b)
                .apiInfo(getApiInfo())
                .groupName("interface 1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ryanalexander.minipro"))
//                .paths(PathSelectors.ant("/ryan/**"))
                .build();

    }

    @Bean
    public Docket docket_2(Environment environment){

        Profiles profiles = Profiles.of("dev");
        boolean b = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(b)
                .apiInfo(getApiInfo())
                .groupName("interface 2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ryanalexander.minipro"))
//                .paths(PathSelectors.ant("/ryan/**"))
                .build();

    }

}
