package cn.ryanalexander.psl.service.tool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="service")
public class StaticConfiguration {
    private Boolean swaggerEnable;
    private Boolean mailSendEnable;

    private String excelReadUrl;
    private String excelWriteUrl;

    private String captchaMailUrl;
    private String feedbackMailUrl;

    private int captchaExpire;
    private int accessExpire;
    private int refreshExpire;

}
