package cn.ryanalexander.auth.service.impl;

import cn.ryanalexander.auth.config.redis.RedisKeyEnum;
import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.auth.mapper.AccountMapper;
import cn.ryanalexander.auth.service.AccountService;
import cn.ryanalexander.auth.service.tool.EmailService;
import cn.ryanalexander.auth.service.tool.StaticConfiguration;
import cn.ryanalexander.common.domain.dto.MailInfo;
import cn.ryanalexander.common.domain.exceptions.*;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.common.service.tool.JwtService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    private void updateKey(String keyName, RedisKeyEnum redisKey, int appKey,
                           String value, int nums){
        try{
            String key = keyName + ":" + redisKey.key + ":" + appKey;
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
    private void verifyKey(String keyName, RedisKeyEnum redisKey, int appKey, String value){
        String key = keyName + ":" + redisKey.key + ":" + appKey;
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
    public void verifyAccess(int accountId, int accountApp, String access){
        verifyKey(String.valueOf(accountId), RedisKeyEnum.ACCESS, accountApp, access);
    }
    public void verifyRefresh(int accountId, int accountApp, String refresh){
        verifyKey(String.valueOf(accountId), RedisKeyEnum.REFRESH, accountApp, refresh);
    }
    // 与上边两个不同 上面的固定式userId作为键 但是这里可能是邮箱
    public void verifyCaptcha(String keyName, int accountApp, String captcha){
        verifyKey(keyName, RedisKeyEnum.CAPTCHA, accountApp, captcha);
    }



    /**
     *
     * @param userId
     * 使用空格分隔双token
     */
    public JSONObject refreshBothToken(int userId, int accountApp){
        String access = JwtService.getAccessToken(userId);
        String refresh = JwtService.getRefreshToken(userId);
        int accessExpire = staticConfiguration.getAccessExpire();
        int refreshExpire = staticConfiguration.getRefreshExpire();

        updateKey(String.valueOf(userId), RedisKeyEnum.ACCESS, accountApp, access, accessExpire);
        updateKey(String.valueOf(userId), RedisKeyEnum.REFRESH, accountApp, refresh, refreshExpire);

        JSONObject json_ = new JSONObject();
        json_.put(RedisKeyEnum.ACCESS.key, access);
        json_.put(RedisKeyEnum.REFRESH.key, refresh);
        return json_;
    }
    // 一定是verify之后再refresh的！verify如果有问题 直接throw exception 然后输出code = 1
    public JSONObject refreshAccess(int userId, int accountApp){
        String access = JwtService.getAccessToken(userId);
        int accessExpire = staticConfiguration.getAccessExpire();
        updateKey(String.valueOf(userId), RedisKeyEnum.ACCESS, accountApp, access, accessExpire);

        JSONObject result = new JSONObject();
        result.put(RedisKeyEnum.ACCESS.key, access);

        return result;
    }

//    @Transactional
    // todo 这里的transactional设计是个问题！
    @Async // 放在emailService.sendCaptchaMails里边就没必要了 一般精确到service即可
    public void getCaptcha(MailInfo mailInfo){
        String captcha = this.generateVerCode();

        try{// callName: 黄继业 | roleName: 老师 | 黄继业老师
            emailService.sendCaptchaMails(captcha,
                    mailInfo.getCallName() + mailInfo.getRoleName(),
                    mailInfo.getMailTo(),
                    mailInfo.getMailHtmlUrl());

            updateKey(mailInfo.getKeyName(),
                    RedisKeyEnum.CAPTCHA,
                    mailInfo.getAccountApp(),
                    captcha,
                    mailInfo.getCaptchaExpire());
        }
        catch (MessagingException | IOException e){
            throw new AppException(e, "Captcha", "getCaptcha");
        }
    }


    // --------------------------
    @Override
    public void updatePwdById(int userId, String userPwd){
        Optional<AccountMapper> accountMapper = Optional.ofNullable(this.accountMapper);

        AccountPO accountPONullable = accountMapper.orElseThrow(
            () -> new InjectionException(AccountService.class)
        ).selectById(userId);

        Optional<AccountPO> accountOptional = Optional.ofNullable(accountPONullable);
        AccountPO accountPO = accountOptional.orElseThrow(
            () -> new NotFoundException(AccountPO.class, "accountMapper.selectById")
        );
        accountPO.setAccountPwd(userPwd);

        this.accountMapper.updateById(accountPO);
    }

    @Override
    public void verifyMail(String mail) {
        if (existMail(mail) != null)
            throw new InvalidException(AccountServiceImpl.class, "verifyMail", "邮箱已经存在了");
    }
    @Override
    public AccountPO existMail(String mail){
        // 不用担心 你select什么 他就填充PO什么字段！
        return accountMapper.selectOne(new QueryWrapper<AccountPO>()
                .select("account_id")
                .eq("account_mail", mail).last("limit 1"));
    }

}




