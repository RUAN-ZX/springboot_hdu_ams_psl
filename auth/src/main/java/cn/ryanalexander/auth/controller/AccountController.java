package cn.ryanalexander.auth.controller;

import cn.ryanalexander.auth.config.redis.RedisKeyEnum;
import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.auth.mapper.AccountMapper;
import cn.ryanalexander.auth.processor.annotationIntercept.Require;
import cn.ryanalexander.auth.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.auth.service.AccountService;
import cn.ryanalexander.common.domain.exceptions.InjectionException;
import cn.ryanalexander.common.domain.exceptions.InvalidException;
import cn.ryanalexander.common.domain.exceptions.NotFoundException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class AccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private AccountMapper accountMapper;
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "type", value = "类型（0:取消分班 1:流失）",required = true,dataType = "int"),
//            @ApiImplicitParam(paramType = "query", name = "studentId", value = "学生id",required = true, dataType = "Long")
//    })
//    @ApiResponses({
//            @ApiResponse(code=0,message = "操作成功",response = JSONObject.class)
//    })
//    @ApiOperation("通过access刷新新access")
//    @GetMapping("/loginByAccess")
//    public Object loginByAccess(String accountId){
//        JSONObject result = new JSONObject();
//        result.put(RedisKeyEnum.ACCESS.key, accountService.refreshAccess(accountId));
//        result.put(RedisKeyEnum.ACCOUNT.key, accountService.getById(accountId).getAccountName());
//        return result;
//    }

    @ApiOperation("验证access的正确性")
    @GetMapping("/verifyAccess")
    public void verifyAccess(String accountId, String access){
        accountService.verifyAccess(accountId, access);
    }

    @ApiOperation("验证refresh的正确性")
    @GetMapping("/verifyRefresh")
    public void verifyRefresh(String accountId, String refresh){
        accountService.verifyRefresh(accountId, refresh);
    }

    @ApiOperation("验证captcha的正确性")
    @GetMapping("/verifyCaptcha")
    public void verifyCaptcha(String accountId, String captcha){
        accountService.verifyCaptcha(accountId, captcha);
    }

    @Require
    @ApiOperation("客户端定时刷新 or 作为loginByAccess的组成Service 续杯5分钟")
    @GetMapping("/refreshAccess")
    public Object refreshAccess(String accountId){
        return accountService.refreshAccess(accountId);
    }

    @ApiOperation("注册或更换邮箱 验证邮箱是否为本人时使用")
    @GetMapping("/sendCaptcha")
    public Object sendCaptcha(String accountMail){
        accountService.getCaptcha(accountMail, "您好","", accountMail);
        return accountMail;
    }

    // 用过一次就失效！
    @Require(RoleEnum.EXPIRED)
    @ApiOperation("通过refresh token更新access token 适用于中间1分钟客户端没跑 但是没超过15天")
    @GetMapping("/refresh")
    public Object refresh(String accountId){
        return accountService.refreshBothToken(accountId);
    }


    @ApiOperation("用户名+密码 | 邮箱+密码 | 手机号+密码 登录")
    @GetMapping("/loginByPwd")
    public Object loginByPwd(String accountId, String accountPwd){
        Optional<AccountPO> accountNullable = Optional.ofNullable(
            accountService.getOne(
                new QueryWrapper<AccountPO>().eq("account_id", accountId)
        ));
        AccountPO accountPO = accountNullable.orElseThrow(() ->
                new InvalidException(AccountPO.class, "getById", "Wrong Account id"));


        if(AccountService.isCaptcha(accountPwd)){
            accountService.verifyCaptcha(accountId,accountPwd);
        }
        else if(accountPO.getAccountPwd() == null){
            throw new NotFoundException(AccountController.class, "loginByPwd", "pwd not found!");
        }
        else if(!accountPO.getAccountPwd().equals(accountPwd)){
            throw new InvalidException(AccountController.class, "loginByPwd", "pwd not right");
        }

        JSONObject jsonObject = accountService.refreshBothToken(accountId);
        jsonObject.put(RedisKeyEnum.ACCOUNT.key, accountPO.getAccountName());
        return jsonObject;
    }

    @ApiOperation("用户已经邮箱注册 获取登记在案的邮箱验证码")
    @GetMapping("/getCaptcha")
    public Object getCaptcha(String accountId, String roleName){
        Optional<AccountPO> accountNullable = Optional.ofNullable(accountService.getById(accountId));
        AccountPO accountPO = accountNullable.orElseThrow(() -> new NotFoundException(AccountPO.class, "accountService.getById"));
        String accountName = accountPO.getAccountName();
        String accountMail = accountPO.getAccountMail();

        accountService.getCaptcha(accountId, accountName, roleName, accountMail);
        return accountMail;
    }



    @Require
    @ApiOperation("更改密码")
    @GetMapping("/updatePwd")
    public Object updatePwd(String accountId, String accountPwd){
        // 现在已经实现了 只要verify出了问题 里边直接throw异常就好 所以解耦的差不多了
        accountService.updatePwdById(accountId,accountPwd);
        return accountPwd;
    }

    public void updatePwdById(String t_id, String t_pwd){
        Optional<AccountMapper> accountMapper = Optional.ofNullable(this.accountMapper);

        AccountPO accountPONullable = accountMapper.orElseThrow(
                () -> new InjectionException(AccountService.class)
        ).selectById(t_id);

        Optional<AccountPO> accountOptional = Optional.ofNullable(accountPONullable);
        AccountPO accountPO = accountOptional.orElseThrow(
                () -> new NotFoundException(AccountPO.class, "accountMapper.selectById")
        );
        accountPO.setAccountPwd(t_pwd);

        this.accountMapper.updateById(accountPO);
    }

    @Require
    @ApiOperation("获取Email地址")
    @GetMapping("/getEmailById")
    public Object getEmailById(String accountId){
        return accountService.getEmailById(accountId);
    }



}



