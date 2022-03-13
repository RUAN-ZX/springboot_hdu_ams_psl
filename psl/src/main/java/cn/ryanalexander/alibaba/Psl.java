package cn.ryanalexander.alibaba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: Main
 * @Description
 * @Author ryan
 * @Date 2022/1/6 14:53
 * @Version 1.0.0-Beta
 **/
@SpringBootApplication()
@MapperScan(basePackages = "cn.ryanalexander.alibaba.dao")
public class Psl {
    public static void main(String[] args) {
        SpringApplication.run(Psl.class, args);
    }
}
