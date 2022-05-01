package cn.ryanalexander.sst.controller;

import cn.ryanalexander.common.domain.exceptions.InvalidException;
import cn.ryanalexander.common.domain.exceptions.NotFoundException;
import cn.ryanalexander.common.enums.AppKeyEnum;
import cn.ryanalexander.sst.config.redis.RedisKeyEnum;
import cn.ryanalexander.sst.domain.po.AccountPO;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.sst.service.AccountFeignService;
import cn.ryanalexander.sst.service.AccountService;
import cn.ryanalexander.sst.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class UserController {
    // 如果该Service有多个实现类，设置注入哪个ServiceImpl类
    // @Qualifier("womanService") 而serviceImpl要制定好名字！@Service("")
    @Resource
    private UserService userService;
    @Resource
    private AccountFeignService accountFeignService;

    @ApiOperation("邮箱注册")
    @PostMapping("/registerByMail")
    public Object registerByMail(String userAlias,
                                 String userPwd,
                                 String userRole,
                                 String userMail,
                                 String captcha){

        return new Object();
    }

    @ApiOperation("已经注册的邮箱 获取验证码")
    @GetMapping("/getCaptcha")
    public Object getCaptcha(String accountId) {
        return new Object();
    }
    @ApiOperation("未注册 未登记的邮箱 获取验证码")
    @GetMapping("/sendCaptcha")
    public Object sendCaptcha(String mailTo){
        // 前端判断即可 毕竟其实就是搭了个桥 然后有些前端判断
        // 有些后端判断（比如auth创建account要是失败了 其实sst这边也没必要创建user了！）
        return accountFeignService.sendCaptcha(mailTo, AppKeyEnum.SST); //
    }

    @Require(RoleEnum.TEACHER)
    @ApiOperation("通过access登录 刷新refresh token")
    @GetMapping("/loginByAccess")
    public Object loginByAccess(String accountId){

        return new Object();
    }

    // 无所谓 是个用户都应该享有这种权利！
    @Require(RoleEnum.TEACHER)
    @ApiOperation("客户端定时刷新 or loginByAccess都行 类似续杯1小时")
    @GetMapping("/refreshAccess")
    public Object refreshAccess(String accountId){
        return new Object();
    }

    // 用过一次就失效！
    @Require(RoleEnum.EXPIRED)
    @ApiOperation("通过refresh token更新access token 适用于中间1分钟客户端没跑 但是没超过15天")
    @GetMapping("/refresh")
    public Object refresh(String accountId){
        return new Object();
    }

    @ApiOperation("登录 或者说注册 反正验证一波")
    @GetMapping("/loginByPwd")
    public Object loginByPwd(String accountId, String accountPwd){

        return new Object();
    }


    @Require(RoleEnum.TEACHER)
    @ApiOperation("更改密码")
    @GetMapping("/updatePwd")
    public Object updatePwd(String accountId, String accountPwd){
        return new Object();
    }
}



