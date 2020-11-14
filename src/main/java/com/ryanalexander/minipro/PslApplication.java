package com.ryanalexander.minipro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync //异步注解？
@EnableScheduling
@MapperScan("com.ryanalexander.minipro.dao")
public class PslApplication {

    public static void main(String[] args) {
        SpringApplication.run(PslApplication.class, args);
    }

}
