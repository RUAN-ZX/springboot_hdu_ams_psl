package cn.ryanalexander.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p><b>被RPC调用的必须使用Async！ 否则远程调用超时 还容易导致重复调用！目前仅针对不返回数据的 发邮件之类的！</b></p>
 * <p><b>OpenFeign在spring cloud Hoxton SR11都没使用Feign的远程Async注解 所以只能是被RPC的auth自己实现了</b></p>

 * <p>2022-05-01 </p>

 * @author RyanAlexander 2022-05-01 9:00
 */
@EnableDiscoveryClient
@MapperScan(basePackages = "cn.ryanalexander.auth.mapper")
@SpringBootApplication
@EnableAsync
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
