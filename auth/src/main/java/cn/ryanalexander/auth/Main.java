package cn.ryanalexander.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ClassName: Main
 * @Description
 * @Author ryan
 * @Date 2022/1/6 14:53
 * @Version 1.0.0-Beta
 **/
@EnableDiscoveryClient
@MapperScan(basePackages = "cn.ryanalexander.auth.mapper")
@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
