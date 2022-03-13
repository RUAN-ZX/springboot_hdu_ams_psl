package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.entities.Payment;
import cn.ryanalexander.alibaba.entities.ResponseStatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: PaymentService
 * @Description
 * @Author Ryan
 * @Date 2021.12.10 21:07
 * @Version 1.0.0-Beta
 **/
@FeignClient(value = "nacos-payment-provider")
public interface PaymentService {

    @GetMapping(value = "/payment/{id}")
    public ResponseStatusCode<Payment> payment(@PathVariable("id") Long id);
}
