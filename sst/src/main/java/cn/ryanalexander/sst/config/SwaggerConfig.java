package cn.ryanalexander.sst.config;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
//        List<RequestParameter> parameters = new ArrayList<>();
//        parameters.add(new RequestParameterBuilder()
//                .name("access").description("access 搭配属性userId认证 若没有userId则无需access").in(ParameterType.HEADER).build());
//        parameters.add(new RequestParameterBuilder()
//                .name("refresh").description("refresh").in(ParameterType.HEADER).build());
        //返回文档摘要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("sst")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ryanalexander.sst"))
                .build();
//                .globalRequestParameters(parameters);
    }

    //生成接口信息，包括标题、联系人等
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SST项目")
                .description("如有疑问，请联系我")
                .contact(new Contact("沈盛涛", "http://sayhitotheworld.ryanalexander.cn/", "1162179851@qq.com"))
                .version("1.0")
                .build();
    }
}
