package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.entities.Payment;
import cn.ryanalexander.alibaba.entities.ResponseStatusCode;
import cn.ryanalexander.alibaba.service.BlockService;
import cn.ryanalexander.alibaba.service.FallbackService;
import cn.ryanalexander.alibaba.service.PaymentService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback",
            fallback = "defaultFallbackHandler",
            fallbackClass = FallbackService.class,
            blockHandlerClass = BlockService.class,
            blockHandler = "defaultBlockHandler")//没有配置
    public ResponseStatusCode<Payment> payment(@PathVariable Long id)
    {
        return paymentService.payment(id);
    }



}
