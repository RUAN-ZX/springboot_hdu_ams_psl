package cn.ryanalexander.alibaba.service.impl;

import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.config.redis.RedisKeyEnum;
import cn.ryanalexander.alibaba.domain.exceptions.*;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
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
    private void updateKey(String accountId, RedisKeyEnum key,
                           String value, int nums){
        try{
            // todo 统一设置为分钟
            redisTemplate.opsForValue().set(accountId+":" + key.key, value, nums, TimeUnit.MINUTES);
        }
        catch (Exception e){
//            throw new AppException(e, RedisTemplate.class.getSimpleName(), "updateKey");
            e.printStackTrace();
        }
    }

    private String generateVerCode(String Tid) {
        char[] nonceChars = new char[6];
        for (int i = 0; i < nonceChars.length; i++) {
            nonceChars[i] = SYMBOLS.charAt(RANDOM.nextInt(nonceChars.length));
        }
        return new String(nonceChars);
    }

    private Boolean verifyKey(String Tid, RedisKeyEnum event, String value){
        String eventName = event.getKey();
        Optional<Object> codeNullable = Optional.ofNullable(
                redisTemplate.opsForValue().get(Tid + ":" + eventName));
        Object code = codeNullable.orElseThrow(() ->
                new AppException(null,
                        new ExceptionInfo(eventName, "redis key 过期 或者压根就没有", "ryanRedisTemplate.opsForValue().get"),
                        new ErrorCode(SubjectEnum.USER)));

        if(code.equals(value)) return true;
        else throw new AppException(null, new ExceptionInfo(eventName, "redis key 错误", "code.equals"), new ErrorCode(SubjectEnum.USER));
    }
    public void verifyAccess(String accountId, String access){
        verifyKey(accountId, RedisKeyEnum.ACCESS, access);
    }
    public void verifyRefresh(String accountId, String refresh){
        verifyKey(accountId, RedisKeyEnum.REFRESH, refresh);
    }
    public void verifyCaptcha(String accountId, String captcha){
        verifyKey(accountId, RedisKeyEnum.CAPTCHA, captcha);
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

        updateKey(Tid, RedisKeyEnum.ACCESS, access, accessExpire);
        updateKey(Tid, RedisKeyEnum.REFRESH, refresh, refreshExpire);

        JSONObject json_ = new JSONObject();
        json_.put(RedisKeyEnum.ACCESS.key, access);
        json_.put(RedisKeyEnum.REFRESH.key, refresh);
        return json_;
    }
    // 一定是verify之后再refresh的！verify如果有问题 直接throw exception 然后输出code = 1
    public String refreshAccess(String Tid){
        String access = JwtService.getAccessToken(Tid);
        int accessExpire = staticConfiguration.getAccessExpire();

        updateKey(Tid, RedisKeyEnum.ACCESS, access, accessExpire);
        return access;
    }

//    @Transactional
    // todo 这里的transactional设计是个问题！
    public void getCaptcha(String accountId, String accountName, String accountMail){
        String captcha = this.generateVerCode(accountId);

        try{// TODO: 2022/3/23 这里还需要改进异步调用 以及异步成功回调updateKey的逻辑！
            emailService.sendCaptchaMails(captcha, accountName, accountMail);
            int captchaExpire = staticConfiguration.getCaptchaExpire();
            updateKey(accountId, RedisKeyEnum.CAPTCHA,
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




