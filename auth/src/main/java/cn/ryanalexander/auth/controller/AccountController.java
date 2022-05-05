package cn.ryanalexander.auth.controller;

import cn.ryanalexander.auth.config.redis.RedisKeyEnum;
import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.auth.mapper.AccountMapper;
import cn.ryanalexander.auth.service.AccountService;
import cn.ryanalexander.common.domain.dto.Account;
import cn.ryanalexander.common.domain.dto.MailInfo;
import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.common.domain.exceptions.InvalidException;
import cn.ryanalexander.common.domain.exceptions.NotFoundException;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 *
 * <p><b>2022-5-2 由于get请求速度快 有缓存 而不安全的特性可以被我们内网环境覆盖！除非黑客劫持了内网中的主机</b></p>
 * <p><b>当然 对于register 更改密码之类的 过于敏感 且涉及到utf-8字符 使用post</b></p>
 * <p><b>客户端则是类似 敏感信息用post 一般的信息获取用get 反正access在请求头里边</b></p>

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
    @PostMapping("/registerAccountInfo")
    public Object registerAccountInfo(@RequestBody Account account){
        // 这里account包含了APP！
        System.out.println(account);
        AccountPO accountPO = new AccountPO(account);
        System.out.println(accountPO);
        accountMapper.insert(accountPO);
        return accountService.refreshBothToken(account.getAccountUserId(), account.getAccountApp()); // mybatis直接把自动生成的id放到实体类里边了 真棒！
    }
    // 覆盖信息 比如改改密码 改改mail之类的 反正具体业务类自己验证 这里就是个通用Service
    @ApiOperation("更改有关认证的信息！")
    @PostMapping("/changeAccountInfo")
    public Object changeAccountInfo(@RequestBody Account account){
        AccountPO accountPO = new AccountPO(account);
        accountService.saveOrUpdate(accountPO);
        return accountPO.getAccountId(); // mybatis直接把自动生成的id放到实体类里边了 真棒！
    }
    // 注意 不能复用register单体 因为效率太低 只能额外处理batch这种情况！
    @ApiOperation("用于批量注册 批量导入用户 存在重复记录的可能！")
    @PostMapping("/registerBatch")
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
    public void verifyAccess(int userId, int accountApp, String access){
        accountService.verifyAccess(userId, accountApp, access);
    }

    @ApiOperation("验证refresh的正确性")
    @GetMapping("/verifyRefresh")
    public void verifyRefresh(int userId, int accountApp, String refresh){
        accountService.verifyRefresh(userId, accountApp, refresh);
    }

    @ApiOperation("验证captcha的正确性")
    @GetMapping("/verifyCaptcha")
    public void verifyCaptcha(String keyName, int accountApp, String captcha){
        accountService.verifyCaptcha(keyName, accountApp, captcha);
    }

    // ==================================================================================

    @ApiOperation("通过refresh token更新access token 适用于中间1分钟客户端没跑 但是没超过15天" +
            "\n也可以用于超过15天或者新用户的登录")
    @GetMapping("/refreshBothToken")
    public Map<String, String> refreshBothToken(int userId, int accountApp){
        return accountService.refreshBothToken(userId, accountApp);
    }

    @ApiOperation("客户端定时刷新 or 作为loginByAccess的组成Service 续杯5分钟")
    @GetMapping("/refreshAccess")
    public Map<String, String> refreshAccess(int userId, int accountApp){
        return accountService.refreshAccess(userId, accountApp);
    }

    // ==================================================================================

    @ApiOperation("注册或更换邮箱 验证邮箱是否为本人时使用")
    @GetMapping("/sendCaptcha")
    public Object sendCaptcha(MailInfo mailInfo){
        accountService.verifyMail(mailInfo.getMailTo()); // 不行会直接throw exception 结束一切！
        mailInfo.setKeyName(mailInfo.getMailTo()); // verify以后可以有资格作为key了！
        accountService.getCaptcha(mailInfo);
        // 您好，您的验证码是。。。
        return "OK";
    }
    //
    // todo 因为暂时不打算让psl创建真正的ACCOUNT 所以口径一致 统一使用teacherId 创建token 验证 都是一个id！
    @ApiOperation("用户已经邮箱注册 获取登记在案的邮箱验证码")
    @GetMapping("/getCaptcha")
    public Object getCaptcha(int userId, MailInfo mailInfo){
        Optional<AccountPO> accountNullable = Optional.ofNullable(
                accountMapper.selectOne(new QueryWrapper<AccountPO>()
                        .eq("account_user_id", userId)
                        .eq("account_app",mailInfo.getAccountApp())
                        .last("limit 1")));

        AccountPO accountPO = accountNullable.orElseThrow(() -> new NotFoundException(AccountPO.class, "accountService.getById"));
//        String accountName = accountPO.getAccountName(); // 应用自己决定吧！
        mailInfo.setMailTo(accountPO.getAccountMail()); // 必须使用auth里边的邮箱！
        mailInfo.setKeyName(String.valueOf(userId));

        accountService.getCaptcha(mailInfo);
        return accountPO.getAccountMail();
    }

    // ==================================================================================

    // todo 设置兼容多种登录方式 用户名+密码 | 邮箱+密码 | 手机号+密码
    @ApiOperation("用户名+密码 | 邮箱+密码 | 手机号+密码 登录")
    @PostMapping("/loginByPwd")
    public Map<String, String> loginByPwd(@RequestBody Account account){
        // 检测这些属性
        String accountName = account.getAccountName();
        String accountMail = account.getAccountMail();
        String accountPhone = account.getAccountPhone();

        int accountApp = account.getAccountApp();
        String accountPwd = account.getAccountPwd();

        Optional<AccountPO> accountNullable;
        // 1 检查是何种登录方式 用户名是名字 邮箱 还是手机号
        if(accountName != null){
            accountNullable = Optional.ofNullable(
                accountService.getOne(
                        new QueryWrapper<AccountPO>().eq("account_name", accountName)
                ));
        }
        else if(accountMail != null){
            accountNullable = Optional.ofNullable(
                    accountService.getOne(
                            new QueryWrapper<AccountPO>().eq("account_mail", accountMail)
                    ));
        }
        else if(accountPhone != null){
            accountNullable = Optional.ofNullable(
                    accountService.getOne(
                            new QueryWrapper<AccountPO>().eq("account_phone", accountPhone)
                    ));
        }
        else throw new InvalidException(AccountController.class,"loginByPwd","登录需要邮箱 手机 用户名 三选一！");

        AccountPO accountPO = accountNullable.orElseThrow(() ->
                new InvalidException(AccountPO.class, "getById", "用户不存在"));

        // 使用各个应用自己的userId 别搞AccountId
        int userId = accountPO.getAccountUserId();

        // 2 检查密码 首先看是不是验证码 不是再设置密码 注意密码不能是6位的数字！
        if(AccountService.isCaptcha(accountPwd)){
            accountService.verifyCaptcha(String.valueOf(userId), accountApp, accountPwd);
        }
        else if(accountPO.getAccountPwd() == null){
            throw new NotFoundException(AccountController.class, "loginByPwd", "未曾设置过密码");
        }
        else if(!accountPO.getAccountPwd().equals(accountPwd)){
            throw new InvalidException(AccountController.class, "loginByPwd", "密码错误");
        }
        // 生成token
        Map<String, String> result = accountService.refreshBothToken(userId, accountApp);
        result.put(RedisKeyEnum.ACCOUNT.key, String.valueOf(userId));
        return result;
    }

    @ApiOperation("更改密码")
    @PostMapping("/updatePwd")
    public Object updatePwd(int userId, int accountApp, String accountPwd){
        // 现在已经实现了 只要verify出了问题 里边直接throw异常就好 所以解耦的差不多了
        accountService.updatePwdById(userId,accountApp, accountPwd);
        return userId;
    }

    // ==================================================================================


}



