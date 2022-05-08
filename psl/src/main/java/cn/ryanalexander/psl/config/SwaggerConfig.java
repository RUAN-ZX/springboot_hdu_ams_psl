package cn.ryanalexander.psl.config;


import cn.ryanalexander.psl.service.tool.StaticConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Resource
    private StaticConfiguration staticConfiguration;

    private ApiInfo getApiInfo(){
        Contact contact = new Contact("阮菜鸡", "https://www.ryanalexander.cn/", "1162179851@qq.com");
        return new ApiInfoBuilder()
                .title("Ryan Document")
                .description("但行好事，莫问前程")
                .version("v1.0.0")
                .contact(contact)
                .build();
    }
    @Bean
    public Docket docket(Environment environment){
//        boolean b = environment.acceptsProfiles(Profiles.of("dev"));

        return new Docket(DocumentationType.OAS_30)
                .enable(staticConfiguration.getSwaggerEnable())
                .apiInfo(getApiInfo())
                .groupName("psl")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ryanalexander.psl"))
                .build();

    }
}
