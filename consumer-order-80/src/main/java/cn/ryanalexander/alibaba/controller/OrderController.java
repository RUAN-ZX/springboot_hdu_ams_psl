package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.domain.Order;
import cn.ryanalexander.alibaba.entities.ResponseStatusCode;
import cn.ryanalexander.alibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: OrderController
 * @Description
 * @Author Ryan
 * @Date 2021/12/11 22:09
 * @Version 1.0.0-Beta
 **/
@RestController
public class OrderController
{
    @Resource
    private OrderService orderService;


    @GetMapping("/order/created")
    public ResponseStatusCode create(Order order)
    {
        orderService.create(order);
        return new ResponseStatusCode(200,"订单创建成功");
    }
}
