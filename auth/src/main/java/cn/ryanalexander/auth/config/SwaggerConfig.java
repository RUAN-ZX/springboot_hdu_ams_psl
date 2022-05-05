package cn.ryanalexander.auth.config;


import cn.ryanalexander.auth.service.tool.StaticConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        //返回文档摘要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("auth")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ryanalexander.auth"))
                .build();
    }

    //生成接口信息，包括标题、联系人等
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AUTH服务")
                .description("如有疑问，请联系我")
                .contact(new Contact("沈盛涛", "http://sayhitotheworld.ryanalexander.cn/", "1162179851@qq.com"))
                .version("1.0")
                .build();
    }
}
