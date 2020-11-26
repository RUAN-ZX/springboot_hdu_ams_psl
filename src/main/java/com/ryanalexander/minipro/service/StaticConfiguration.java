package com.ryanalexander.minipro.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="service")
public class StaticConfiguration {
    private String excelurl;
    private String mailurl;


}
