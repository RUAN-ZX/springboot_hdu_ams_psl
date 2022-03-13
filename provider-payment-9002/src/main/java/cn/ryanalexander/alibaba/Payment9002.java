package cn.ryanalexander.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: Payment9002
 * @Description
 * @Author Ryan
 * @Date 2021.12.7 11:54
 * @Version 1.0.0-Beta
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class Payment9002 {
    public static void main(String[] args) {
        SpringApplication.run(Payment9002.class,args);
    }
}
