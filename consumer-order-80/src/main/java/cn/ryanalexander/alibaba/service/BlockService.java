package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.entities.Payment;
import cn.ryanalexander.alibaba.entities.ResponseStatusCode;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: BlockService
 * @Description
 * @Author Ryan
 * @Date 2021.12.10 21:29
 * @Version 1.0.0-Beta
 **/
public class BlockService {
    public static ResponseStatusCode<Payment> defaultBlockHandler(@PathVariable Long id, Throwable e) {

        return new ResponseStatusCode<Payment>(400,"handlerFallback,exception内容  "+e.getMessage());
    }
}
