package cn.ryanalexander.alibaba.service.tool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="service")
public class StaticConfiguration {
    private Boolean swaggerEnable;
    private String excelReadUrl;
    private String excelWriteUrl;
    private String mailUrl;
    private int captchaExpire;
    private int accessExpire;
    private int refreshExpire;

}
