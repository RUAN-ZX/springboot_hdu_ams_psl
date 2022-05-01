package cn.ryanalexander.psl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: Main
 * @Description
 * @Author ryan
 * @Date 2022/1/6 14:53
 * @Version 1.0.0-Beta
 **/
@EnableDiscoveryClient
@SpringBootApplication()
@MapperScan(basePackages = "cn.ryanalexander.psl.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
