package cn.ryanalexander.psl.service.tool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix="service")
public class StaticConfiguration {
    private Boolean swaggerEnable;

    private Boolean testMailEnable;
    private List<String> testMailList;

    private String excelReadUrl;
    private String excelWriteUrl;

    private String captchaMailUrl;
    private String feedbackMailUrl;

    private int captchaExpire;
    private int accessExpire;
    private int refreshExpire;

}
