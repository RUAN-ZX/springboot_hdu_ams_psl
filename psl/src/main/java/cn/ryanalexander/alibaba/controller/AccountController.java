package cn.ryanalexander.alibaba.controller;


import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.enumable.KeyEnum;
import cn.ryanalexander.alibaba.domain.exceptions.InvalidException;
import cn.ryanalexander.alibaba.domain.exceptions.NotFoundException;
import cn.ryanalexander.alibaba.domain.exceptions.UnKnownException;
import cn.ryanalexander.alibaba.mapper.AccountDao;
import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.service.*;
import cn.ryanalexander.alibaba.service.tool.EmailService;
import cn.ryanalexander.alibaba.service.tool.ErrorService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class AccountController {
    // 如果该Service有多个实现类，设置注入哪个ServiceImpl类
    // @Qualifier("womanService") 而serviceImpl要制定好名字！@Service("")
    @Resource
    private AccountService accountService;

    @Resource
    private EmailService emailservice;

    @Resource
    private AccountDao accountDao;


    @ApiOperation("通过token验证")
    @PostMapping("/loginByAccess")
    public Result loginByaccess(String Tid, String access){
        accountService.verifyAccess(Tid,access)
        JSONObject jsonObject = accountService.refreshBothToken(Tid);
        jsonObject.put(KeyEnum.ACCOUNT.key, accountService.getById(Tid).getAccountName());
        return new Result(ErrorCodeEnum.SUCCESS, jsonObject);
    }

    @ApiOperation("通过refresh token更新access token")
    @PostMapping("/refresh")
    public Result refresh(String Tid, String refresh){
//        if(accountService.verifyRefresh(Tid,refresh)){
//            JSONObject jsonObject = accountService.refreshBothToken(Tid);
//            jsonObject.put(KeyEnum.ACCOUNT.key, accountService.getById(Tid).getAccountName());
//            return new Result(ErrorCodeEnum.SUCCESS, jsonObject);
//        }
//        else throw new UnKnownException(AccountController.class, "refresh");
        accountService.verifyRefresh(Tid,refresh); // 如果有问题 里边就排除完毕 不需要外边看了
        JSONObject jsonObject = accountService.refreshBothToken(Tid);
        jsonObject.put(KeyEnum.ACCOUNT.key, accountService.getById(Tid).getAccountName());
        return new Result(ErrorCodeEnum.SUCCESS, jsonObject);
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
    @PostMapping("/loginByPwd")
    public Result loginByPwd(String accountId, String accountPwd) throws Exception {
//        String accountId = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("accountId");
//
//        String accountPwd = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("accountPwd");
        Optional<AccountPO> accountNullable = Optional.ofNullable(accountService.getById(accountId));
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
        jsonObject.put(KeyEnum.ACCOUNT.key, accountPO.getAccountName());
        return new Result(ErrorCodeEnum.SUCCESS, jsonObject);
    }
    @ApiOperation("获取验证码")
    @PostMapping("/getCaptcha")
    public Result getCaptcha(String Tid) throws Exception {
        try{
            Optional<AccountPO> accountNullable = Optional.ofNullable(accountService.getById(Tid));
            AccountPO accountPO = accountNullable.orElseThrow(() -> new NotFoundException(AccountPO.class, "accountService.getById"));
            String Tname = accountPO.getAccountName();
            String Temail = accountPO.getAccountMail();

            accountService.getCaptcha(Tid, Tname, Temail);
            return new Result(ErrorCodeEnum.SUCCESS, "验证码已发送到您的邮箱"+Temail+" 10分钟内有效");
        }
        catch (Exception e){
            return new Result(ErrorCodeEnum.FAIL, "您的教工号可能输错了");
        }
    }


    @ApiOperation("更改密码")
    @PostMapping("/updatePwd")
    public Result updatePwd(String accountId, String accountPwd, String access){
        // TODO: 2022/3/23 迟早需要设计成 打了注解的标注必须verify access token 没打的不用 这种形式！
        // 现在已经实现了 只要verify出了问题 里边直接throw异常就好 所以解耦的差不多了
        accountService.verifyAccess(accountId,access);
        accountService.updatePwdById(accountId,accountPwd);
        return new Result(ErrorCodeEnum.SUCCESS, "修改完成");
    }
    @ApiOperation("获取Email地址")
    @PostMapping("/getEmailById")
    public Result getEmailById(String accountId, String access){
        accountService.verifyAccess(
                accountService.getEmailById(accountId), accountId, access)
        return new Result(ErrorCodeEnum.SUCCESS, "修改完成");
    }
}



