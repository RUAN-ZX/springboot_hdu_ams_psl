package cn.ryanalexander.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/12 </p>
 *
 * @author ryan 2022/5/12 9:35
 * @since 1.0.0
 **/

//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
