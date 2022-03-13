package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.entities.ResponseStatusCode;
import cn.ryanalexander.alibaba.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: StroageController
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:50
 * @Version 1.0.0-Beta
 **/
@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    /**
     * 扣减库存
     */
    @PostMapping("/storage/decrease")
    public ResponseStatusCode decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return new ResponseStatusCode(200,"扣减库存成功！");
    }
}
