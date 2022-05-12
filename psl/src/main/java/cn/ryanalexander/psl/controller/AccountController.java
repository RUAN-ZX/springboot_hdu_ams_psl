package cn.ryanalexander.psl.controller;


import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.psl.config.redis.RedisKeyEnum;
import cn.ryanalexander.psl.domain.exceptions.InvalidException;
import cn.ryanalexander.psl.domain.exceptions.NotFoundException;
import cn.ryanalexander.psl.domain.po.AccountPO;
import cn.ryanalexander.psl.processor.annotationIntercept.Require;
import cn.ryanalexander.psl.processor.annotationIntercept.RoleEnum;

import cn.ryanalexander.psl.service.AccountService;
import cn.ryanalexander.psl.service.tool.EmailService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@RestController
public class AccountController {
    // 如果该Service有多个实现类，设置注入哪个ServiceImpl类
    // @Qualifier("womanService") 而serviceImpl要制定好名字！@Service("")
    @Resource
    private AccountService accountService;
    @Resource
    private EmailService emailService;

    @Require(RoleEnum.TEACHER)
    @ApiOperation("通过access登录 刷新refresh token")
    @GetMapping("/loginByAccess")
    public Object loginByAccess(String accountId){
        JSONObject result = new JSONObject();
        result.put(RedisKeyEnum.ACCESS.key, accountService.refreshAccess(accountId));
        result.put(RedisKeyEnum.ACCOUNT.key, accountService.getById(accountId).getAccountName());
        return result;
    }

    // 无所谓 是个用户都应该享有这种权利！
    @Require(RoleEnum.TEACHER)
    @ApiOperation("客户端定时刷新 or loginByAccess都行 类似续杯1小时")
    @GetMapping("/refreshAccess")
    public Object refreshAccess(String accountId){
        return accountService.refreshAccess(accountId);
    }

    // 用过一次就失效！
    @Require(RoleEnum.EXPIRED)
    @ApiOperation("通过refresh token更新access token 适用于中间1分钟客户端没跑 但是没超过15天")
    @GetMapping("/refresh")
    public Object refresh(String accountId){
        JSONObject jsonObject = accountService.refreshBothToken(accountId);
        jsonObject.put(RedisKeyEnum.ACCOUNT.key, accountService.getById(accountId).getAccountName());
        return jsonObject;
    }

    /**
     *
     * return token!!! or error code!!!!
     *
     *
     * 检测到输入为6位数字 -> 且验证码字段为空 则验证码过期了
     *  *              验证码字段不为空 但是错了 那就是验证码错误
     *      *      检测到其他输入 却是第一次使用本系统 没有密码 -> 还没有注册过密码 请先使用验证码登录 并注册自己的密码 以为未来登录时使用
     *      *      检测到其他输入 密码错误 ——>密码错误！
     */

    /**
     * 1 用户登录 传输使用jwt生成的info token [token(accountId accountPwd)]
     * 2 服务器生成access token与refresh token 【token(accountId)】
     *
     */
    @ApiOperation("登录 或者说注册 反正验证一波")
    @GetMapping("/loginByPwd")
    public Object loginByPwd(String accountId, String accountPwd){
//        String accountId = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("accountId");
//
//        String accountPwd = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("accountPwd");
        Optional<AccountPO> accountNullable = Optional.ofNullable(
            accountService.getOne(
                new QueryWrapper<AccountPO>().eq("account_id", accountId)
        ));
        AccountPO accountPO = accountNullable.orElseThrow(() ->
                new InvalidException(AccountPO.class, "getById", "Wrong Account id"));

//        boolean isWrongCaptcha = AccountService.isCaptcha(accountPwd) &&
//                !accountService.verifyCaptcha(accountId,accountPwd);
//        // 如果verify里边显示为true 但是这里却炸了 只能说不可能。
//        if(isWrongCaptcha){
//            throw new UnKnownException(AccountController.class, "loginByPwd");
//        }// 普通密码

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
    @ApiOperation("获取验证码")
    @GetMapping("/getCaptcha")
    public Object getCaptcha(String accountId){
        Optional<AccountPO> accountNullable = Optional.ofNullable(accountService.getById(accountId));
        AccountPO accountPO = accountNullable.orElseThrow(() -> new NotFoundException(AccountPO.class, "accountService.getById"));
        String Tname = accountPO.getAccountName();
        String Temail = accountPO.getAccountMail();

        accountService.getCaptcha(accountId, Tname, Temail);
        return Temail;
    }

    @Require(RoleEnum.TEACHER)
    @ApiOperation("更改密码")
    @GetMapping("/updatePwd")
    public Object updatePwd(String accountId, String accountPwd){
        // 现在已经实现了 只要verify出了问题 里边直接throw异常就好 所以解耦的差不多了
        accountService.updatePwdById(accountId,accountPwd);
        return accountPwd;
    }

    @Require(RoleEnum.TEACHER)
    @ApiOperation("获取Email地址")
    @GetMapping("/getEmailById")
    public Object getEmailById(String accountId){
        return accountService.getEmailById(accountId);
    }

    @Require(RoleEnum.TEACHER)
    @ApiOperation("教师发送反馈")
    @PostMapping("/sendFeedback")
    public Result sendFeedback(String accountId,
                               String accountName,
                               String feedback,
                               String topic) throws MessagingException, IOException {
        emailService.sendFeedbackMails(accountId, accountName, feedback, topic);
        return new Result();
    }
}



