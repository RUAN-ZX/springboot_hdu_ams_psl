package cn.ryanalexander.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: Payment9001
 * @Description
 * @Author Ryan
 * @Date 2021.12.6 16:42
 * @Version 1.0.0-Beta
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class Payment9001 {
    public static void main(String[] args) {
        SpringApplication.run(Payment9001.class, args);
    }
}
