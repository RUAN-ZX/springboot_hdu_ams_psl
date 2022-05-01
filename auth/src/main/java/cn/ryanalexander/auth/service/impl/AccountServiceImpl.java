package cn.ryanalexander.auth.service.impl;

import cn.ryanalexander.auth.config.redis.RedisKeyEnum;
import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.auth.mapper.AccountMapper;
import cn.ryanalexander.auth.service.AccountService;
import cn.ryanalexander.auth.service.tool.EmailService;
import cn.ryanalexander.auth.service.tool.StaticConfiguration;
import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.common.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.common.domain.exceptions.InjectionException;
import cn.ryanalexander.common.domain.exceptions.NotFoundException;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.common.enums.AppKeyEnum;
import cn.ryanalexander.common.service.tool.JwtService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
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
    private RedisTemplate<String, Object> redisTemplate;
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

    // TODO: 2022/3/24 这里的逻辑还有大问题 如果redis主键更新了 mail炸了 或者反之 这个rollback是个大问题！
    private void updateKey(String keyName, RedisKeyEnum redisKey, AppKeyEnum appKey,
                           String value, int nums){
        try{
            String key = keyName + ":" + redisKey.key + ":" + appKey.key;
            // todo 统一设置为分钟
            redisTemplate.opsForValue().set(key, value, nums, TimeUnit.MINUTES);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateVerCode() {
        char[] nonceChars = new char[6];
        for (int i = 0; i < nonceChars.length; i++) {
            nonceChars[i] = SYMBOLS.charAt(RANDOM.nextInt(nonceChars.length));
        }
        return new String(nonceChars);
    }
    // 反正总是会报错 不如统一为Result接口 对方就不会收到意外结果
    private void verifyKey(String keyName, RedisKeyEnum redisKey, AppKeyEnum appKey, String value){
        String key = keyName + ":" + redisKey.key + ":" + appKey.key;
        Optional<Object> codeNullable = Optional.ofNullable(
                redisTemplate.opsForValue().get(key));
        Object code = codeNullable.orElseThrow(() ->
                new AppException(null,
                        new ExceptionInfo(redisKey.key, "redis key 过期 或者压根就没有", "ryanRedisTemplate.opsForValue().get"),
                        new ErrorCode(SubjectEnum.USER)));

        if(!code.equals(value)) throw new AppException(
                null, new ExceptionInfo(redisKey.key, "redis key 错误", "code.equals"),
                new ErrorCode(SubjectEnum.USER));
    }
    public void verifyAccess(String accountId, AppKeyEnum accountApp, String access){
        verifyKey(accountId, RedisKeyEnum.ACCESS, accountApp, access);
    }
    public void verifyRefresh(String accountId, AppKeyEnum accountApp, String refresh){
        verifyKey(accountId, RedisKeyEnum.REFRESH, accountApp, refresh);
    }
    public void verifyCaptcha(String accountId, AppKeyEnum accountApp, String captcha){
        verifyKey(accountId, RedisKeyEnum.CAPTCHA, accountApp, captcha);
    }



    /**
     *
     * @param Tid
     * 使用空格分隔双token
     */
    public JSONObject refreshBothToken(String Tid, AppKeyEnum accountApp){
        String access = JwtService.getAccessToken(Tid);
        String refresh = JwtService.getRefreshToken(Tid);
        int accessExpire = staticConfiguration.getAccessExpire();
        int refreshExpire = staticConfiguration.getRefreshExpire();

        updateKey(Tid, RedisKeyEnum.ACCESS, accountApp, access, accessExpire);
        updateKey(Tid, RedisKeyEnum.REFRESH, accountApp, refresh, refreshExpire);

        JSONObject json_ = new JSONObject();
        json_.put(RedisKeyEnum.ACCESS.key, access);
        json_.put(RedisKeyEnum.REFRESH.key, refresh);
        return json_;
    }
    // 一定是verify之后再refresh的！verify如果有问题 直接throw exception 然后输出code = 1
    public JSONObject refreshAccess(String Tid, AppKeyEnum accountApp){
        String access = JwtService.getAccessToken(Tid);
        int accessExpire = staticConfiguration.getAccessExpire();
        updateKey(Tid, RedisKeyEnum.ACCESS, accountApp, access, accessExpire);

        JSONObject result = new JSONObject();
        result.put(RedisKeyEnum.ACCESS.key, access);

        return result;
    }

//    @Transactional
    // todo 这里的transactional设计是个问题！
    @Async // 放在emailService.sendCaptchaMails里边就没必要了 一般精确到service即可
    public void getCaptcha(String keyName, AppKeyEnum accountApp, String callName, String roleName, String mailTo){
        String captcha = this.generateVerCode();

        try{// callName: 黄继业 | roleName: 老师 | 黄继业老师
            emailService.sendCaptchaMails(captcha, callName + roleName, mailTo);
//            int captchaExpire = 24*60*2;
            int captchaExpire = staticConfiguration.getCaptchaExpire();
            updateKey(keyName, RedisKeyEnum.CAPTCHA, accountApp,
                    captcha, captchaExpire);
        }
        catch (MessagingException | IOException e){
            throw new AppException(e, "Captcha", "getCaptcha");
        }
    }


    // --------------------------
    @Override
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

    public String getEmailById(String Tid) {
        Optional<AccountPO> accountOptional =
                Optional.ofNullable(accountMapper.selectById(Tid));

        AccountPO accountPO = accountOptional.orElseThrow(
                () -> new NotFoundException(AccountPO.class, "accountMapper.selectById")
        );
        // TODO: 2022/3/23 应当重写这种select方法为好 主要是service层需要添加Optional 省的每次调用都要头疼是否捕获成功的问题！

        return accountPO.getAccountMail();
    }
}




