package cn.ryanalexander.alibaba.service.impl;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.enumable.KeyEnum;
import cn.ryanalexander.alibaba.domain.exceptions.*;
import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.AccountService;
import cn.ryanalexander.alibaba.service.tool.EmailService;
import cn.ryanalexander.alibaba.service.tool.JwtService;
import cn.ryanalexander.alibaba.service.tool.StaticConfiguration;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2022-03-22 16:11:53
*/
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountPO>
    implements AccountService {
    @Resource
    AccountMapper accountMapper;
    @Resource
    private RedisTemplate<String, Object> ryanRedisTemplate;
    @Resource
    private AccountMapper accountService;
    @Resource
    private EmailService emailService;
    @Resource
    StaticConfiguration staticConfiguration;
    private static final String SYMBOLS = "0123456789";
    /**
     * Math.random生成的是一般随机数，采用的是类似于统计学的随机数生成规则，其输出结果很容易预测，因此可能导致被攻击者击中。
     * 而SecureRandom是真随机数，采用的是类似于密码学的随机数生成规则，其输出结果较难预测，若想要预防被攻击者攻击，最好做到使攻击者根本无法，或不可能鉴别生成的随机值和真正的随机值。
     */
    private static final Random RANDOM = new SecureRandom();

    private void updateKey(String accountId, KeyEnum key,
                           String value, int nums, TimeUnit timeUnit){
        ryanRedisTemplate.opsForValue().set(accountId+":" + key.key, value, nums, timeUnit);
    }

    private String generateVerCode(String Tid) {
        char[] nonceChars = new char[6];
        for (int i = 0; i < nonceChars.length; i++) {
            nonceChars[i] = SYMBOLS.charAt(RANDOM.nextInt(nonceChars.length));
        }
        return new String(nonceChars);
    }
    private Boolean verifyKey(String Tid, KeyEnum event, String value){
        String eventName = event.getKey();
        Optional<Object> codeNullable = Optional.ofNullable(
                ryanRedisTemplate.opsForValue().get(Tid + ":" + eventName));
        Object code = codeNullable.orElseThrow(() ->
                new AppException(new ExceptionInfo(eventName, "redis key 过期 或者压根就没有", "ryanRedisTemplate.opsForValue().get"), ErrorCodeEnum.NOT_Found));

        if(code.equals(value)) return true;
        else throw new AppException(new ExceptionInfo(eventName, "redis key 错误", "code.equals"), ErrorCodeEnum.INVALID);
    }
    public void verifyAccess(String accountId, String access){
        verifyKey(accountId, KeyEnum.ACCESS, access);
    }
    public void verifyRefresh(String accountId, String refresh){
        verifyKey(accountId, KeyEnum.REFRESH, refresh);
    }
    public void verifyCaptcha(String accountId, String captcha){
        verifyKey(accountId, KeyEnum.CAPTCHA, captcha);
    }

    @Async
    public void getCaptcha(String accountId, String accountName, String accountMail){
        String captcha = this.generateVerCode(accountId);
        try{// TODO: 2022/3/23 这里还需要改进异步调用 以及异步成功回调updateKey的逻辑！
            emailService.sendCaptchaMails(captcha, accountName, accountMail);
        }
        catch (Exception e){
            log.warn(e.getMessage());
        }

        int captchaExpire = staticConfiguration.getCaptchaExpire();
        updateKey(accountId, KeyEnum.CAPTCHA,
                captcha, captchaExpire, TimeUnit.MINUTES);
    }

    /**
     *
     * @param Tid
     * 使用空格分隔双token
     */
    public JSONObject refreshBothToken(String Tid){
        String access = JwtService.getAccessToken(Tid);
        String refresh = JwtService.getRefreshToken(Tid);
        int accessExpire = staticConfiguration.getAccessExpire();
        int refreshExpire = staticConfiguration.getRefreshExpire();

        updateKey(Tid, KeyEnum.ACCESS, access, accessExpire,TimeUnit.DAYS);
        updateKey(Tid, KeyEnum.REFRESH, refresh, refreshExpire,TimeUnit.DAYS);

        JSONObject json_ = new JSONObject();
        json_.put(KeyEnum.ACCESS.key, access);
        json_.put(KeyEnum.REFRESH.key, refresh);
        return json_;
    }
    // --------------------------
    @Override
    public void updatePwdById(String t_id, String t_pwd){
        Optional<AccountMapper> accountMapper = Optional.ofNullable(this.accountMapper);

        AccountPO accountPONullable = accountMapper.orElseThrow(
            () -> new InjectionException(AccountService.class)
        ).selectById(Integer.valueOf(t_id));

        Optional<AccountPO> accountOptional = Optional.ofNullable(accountPONullable);
        AccountPO accountPO = accountOptional.orElseThrow(
            () -> new NotFoundException(AccountPO.class, "accountMapper.selectById")
        );
        accountPO.setAccountPwd(t_pwd);

        this.accountMapper.updateById(accountPO);
    }

    public String getEmailById(String Tid) {
        Optional<AccountPO> accountOptional =
                Optional.ofNullable(accountMapper.selectById(Integer.valueOf(Tid)));

        AccountPO accountPO = accountOptional.orElseThrow(
                () -> new NotFoundException(AccountPO.class, "accountMapper.selectById")
        );
        // TODO: 2022/3/23 应当重写这种select方法为好 主要是service层需要添加Optional 省的每次调用都要头疼是否捕获成功的问题！

        return accountPO.getAccountMail();
    }
}




