package cn.ryanalexander.auth.controller;

import cn.ryanalexander.auth.config.redis.RedisKeyEnum;
import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.auth.mapper.AccountMapper;
import cn.ryanalexander.auth.processor.annotationIntercept.Require;
import cn.ryanalexander.auth.service.AccountService;
import cn.ryanalexander.common.domain.dto.Account;
import cn.ryanalexander.common.domain.exceptions.InjectionException;
import cn.ryanalexander.common.domain.exceptions.InvalidException;
import cn.ryanalexander.common.domain.exceptions.NotFoundException;
import cn.ryanalexander.common.enums.AppKeyEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * <p><b>这里 因为nginx保证近乎内网的安全环境 且相信各个APP都配置相应拦截器 完成access refresh等验证</b></p>
 * <p><b>故这里不设置postProcessor之类的检查 直接执行 就类似内部的service 节约性能</b></p>
 * <p><b>主要考虑数据获取的时候 只需要验证一下即可 所以干脆所有检查交给各个APP处理 包括复杂的Role控制 </b></p>
 * <p><b>比如PSL 只有教务处管理员级别才有资格导入普通账户的AccountInfo</b></p>
 *
 * <p><b>2022-5-1 因为时间有限 目前计划尽量减少auth对PSL的侵入性 只会使用redis相关的验证</b></p>
 * <p><b>使用的“accountId”统一口径 为teacherId！</b></p>

 * <p>2022-05-01 </p>

 * @since
 * @author RyanAlexander 2022-05-01 19:43
 */
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
    @ApiOperation("单体用户注册")
    @GetMapping("/registerAccountInfo")
    public Object registerAccountInfo(Account account){
        // 这里account包含了APP！
        AccountPO accountPO = new AccountPO(account);
        accountService.save(accountPO);
        return accountPO.getAccountId(); // mybatis直接把自动生成的id放到实体类里边了 真棒！
    }
    // 覆盖信息 比如改改密码 改改mail之类的 反正具体业务类自己验证 这里就是个通用Service
    @Require
    @ApiOperation("更改有关认证的信息！")
    @GetMapping("/changeAccountInfo")
    public Object changeAccountInfo(Account account){
        AccountPO accountPO = new AccountPO(account);
        accountService.saveOrUpdate(accountPO);
        return accountPO.getAccountId(); // mybatis直接把自动生成的id放到实体类里边了 真棒！
    }
    // 注意 不能复用register单体 因为效率太低 只能额外处理batch这种情况！
    @ApiOperation("用于批量注册 批量导入用户 存在重复记录的可能！")
    @GetMapping("/registerBatch")
    public Object registerBatch(ArrayList<Account> accounts){
        // todo HDU教务查目前根本用不到！ 目前不打算验证与实现
        accountMapper.saveOrIgnoreBatchByAccount(accounts);
        // 需要自己实现 mybatis实现
        return accountMapper.selectObjs(new QueryWrapper<AccountPO>().
                select("account_id"));
    }

    // ==================================================================================

    @ApiOperation("验证access的正确性")
    @GetMapping("/verifyAccess")
    public void verifyAccess(String accountId, String access, AppKeyEnum accountApp){
        accountService.verifyAccess(accountId, accountApp, access);
    }

    @ApiOperation("验证refresh的正确性")
    @GetMapping("/verifyRefresh")
    public void verifyRefresh(String accountId, String refresh, AppKeyEnum accountApp){
        accountService.verifyRefresh(accountId, accountApp, refresh);
    }

    @ApiOperation("验证captcha的正确性")
    @GetMapping("/verifyCaptcha")
    public void verifyCaptcha(String accountId, String captcha, AppKeyEnum accountApp){
        accountService.verifyCaptcha(accountId, accountApp, captcha);
    }

    // ==================================================================================

    @ApiOperation("通过refresh token更新access token 适用于中间1分钟客户端没跑 但是没超过15天" +
            "\n也可以用于超过15天或者新用户的登录")
    @GetMapping("/refreshBothToken")
    public Object refreshBothToken(String accountId, AppKeyEnum accountApp){
        return accountService.refreshBothToken(accountId, accountApp);
    }

    @ApiOperation("客户端定时刷新 or 作为loginByAccess的组成Service 续杯5分钟")
    @GetMapping("/refreshAccess")
    public Object refreshAccess(String accountId, AppKeyEnum accountApp){
        return accountService.refreshAccess(accountId, accountApp);
    }

    // ==================================================================================

    @ApiOperation("注册或更换邮箱 验证邮箱是否为本人时使用")
    @GetMapping("/sendCaptcha")
    public Object sendCaptcha(String accountMail, AppKeyEnum accountApp){
        accountService.getCaptcha(accountMail, accountApp, "您好","", accountMail);
        return accountMail;
    }
    // 目前是 用户登录会获取两个ID 一个是用于认证的accountId 一个是用于查询的userId
    // todo 因为暂时不打算让psl创建真正的ACCOUNT 所以口径一致 统一使用teacherId 创建token 验证 都是一个id！
    @ApiOperation("用户已经邮箱注册 获取登记在案的邮箱验证码")
    @GetMapping("/getCaptcha")
    public Object getCaptcha(String accountId, AppKeyEnum accountApp, String roleName){
        Optional<AccountPO> accountNullable = Optional.ofNullable(accountService.getById(accountId));
        AccountPO accountPO = accountNullable.orElseThrow(() -> new NotFoundException(AccountPO.class, "accountService.getById"));
        String accountName = accountPO.getAccountName();
        String accountMail = accountPO.getAccountMail();

        accountService.getCaptcha(accountId, accountApp, accountName, roleName, accountMail);
        return accountMail;
    }

    // ==================================================================================

    @ApiOperation("用户名+密码 | 邮箱+密码 | 手机号+密码 登录")
    @GetMapping("/loginByPwd")
    public Object loginByPwd(String accountId, AppKeyEnum accountApp, String accountPwd){
        Optional<AccountPO> accountNullable = Optional.ofNullable(
            accountService.getOne(
                new QueryWrapper<AccountPO>().eq("account_id", accountId)
        ));
        AccountPO accountPO = accountNullable.orElseThrow(() ->
                new InvalidException(AccountPO.class, "getById", "Wrong Account id"));


        if(AccountService.isCaptcha(accountPwd)){
            accountService.verifyCaptcha(accountId, accountApp, accountPwd);
        }
        else if(accountPO.getAccountPwd() == null){
            throw new NotFoundException(AccountController.class, "loginByPwd", "pwd not found!");
        }
        else if(!accountPO.getAccountPwd().equals(accountPwd)){
            throw new InvalidException(AccountController.class, "loginByPwd", "pwd not right");
        }

        JSONObject jsonObject = accountService.refreshBothToken(accountId, accountApp);
        jsonObject.put(RedisKeyEnum.ACCOUNT.key, accountPO.getAccountName());
        return jsonObject;
    }

    @ApiOperation("更改密码")
    @GetMapping("/updatePwd")
    public Object updatePwd(String accountId, String accountPwd){
        // 现在已经实现了 只要verify出了问题 里边直接throw异常就好 所以解耦的差不多了
        accountService.updatePwdById(accountId, accountPwd);
        return accountPwd;
    }

}



