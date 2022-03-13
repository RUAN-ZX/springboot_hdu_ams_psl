package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.config.DataSourceProxyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @ClassName: OrderMain80
 * @Description
 * @Author Ryan
 * @Date 2021.12.10 16:21
 * @Version 1.0.0-Beta
 **/

@EnableDiscoveryClient
// 不使用默认的自动装配的配置 而是我们自己的配置
// 因为要配置多数据源，上边有提到DataSourceAutoConfiguration.class默认会帮我们自动配置单数据源，
// 所以，如果想在项目中使用多数据源就需要排除它，手动指定多数据源。
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
