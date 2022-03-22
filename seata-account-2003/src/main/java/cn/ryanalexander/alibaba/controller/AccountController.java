package cn.ryanalexander.alibaba.controller;

import cn.ryanalexander.alibaba.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import cn.ryanalexander.alibaba.domain.dto.Result;
import java.math.BigDecimal;

/**
 * @ClassName: AccountController
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:56
 * @Version 1.0.0-Beta
 **/
@RestController // 注意！ 这里是RestController！！！！！ 包含了ResponseBody
// 否则微服务 Feigh不承认 这样会导致执行操作但是返回的东西不被承认 最终导致consumer那边的失败
public class AccountController {
    @Resource
    AccountService accountService;

    /**
     * 扣减账户余额
     */
    @PostMapping("/account/decrease")
    public Result<String> decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        accountService.decrease(userId,money);
        return new Result<String>(200,"扣减账户余额成功！");
    }
}
