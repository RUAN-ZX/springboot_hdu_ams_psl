package com.ryanalexander.minipro.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="service")
public class StaticConfiguration {
    private String excelUrl;
    private String mailUrl;
    private int captchaExpire;
    private int accessExpire;
    private int refreshExpire;

}
