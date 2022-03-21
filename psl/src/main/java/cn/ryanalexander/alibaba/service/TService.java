package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.dao.AccountDao;
import cn.ryanalexander.alibaba.dao.TeacherDao;
import cn.ryanalexander.alibaba.entity.Teacher;
import cn.ryanalexander.alibaba.service.tool.ErrorService;
import cn.ryanalexander.alibaba.service.tool.JwtService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Random;


@Service
@Slf4j
public class TService {
    @Resource
    TeacherDao tDao;

    @Resource
    AccountDao accountDao;

    @Autowired
    private RedisTemplate<String, Object> ryanRedisTemplate;
    private static final String SYMBOLS = "0123456789";
    /**
     * Math.random生成的是一般随机数，采用的是类似于统计学的随机数生成规则，其输出结果很容易预测，因此可能导致被攻击者击中。
     * 而SecureRandom是真随机数，采用的是类似于密码学的随机数生成规则，其输出结果较难预测，若想要预防被攻击者攻击，最好做到使攻击者根本无法，或不可能鉴别生成的随机值和真正的随机值。
     */
    private static final Random RANDOM = new SecureRandom();

    public static String generateVerCode(String Tid) {
        char[] nonceChars = new char[6];
        for (int i = 0; i < nonceChars.length; i++) {
            nonceChars[i] = SYMBOLS.charAt(RANDOM.nextInt(nonceChars.length));
        }
        return new String(nonceChars);
    }
    /**
     *
     * @param Tid
     * 使用空格分隔双token
     */
    public JSONObject refreshBothToken(String Tid){
        String access = JwtService.getAccessToken(Tid);
        String refresh = JwtService.getRefreshToken(Tid);
        accountDao.TupdateAccess(access,Tid);
        accountDao.TupdateRefresh(refresh,Tid);

        JSONObject json_ = new JSONObject();
        json_.put("a",access);
        json_.put("r",refresh);
        return json_;

    }

    public static boolean iscaptcha(String str) {
        if(str.length()!=6) return false;
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public JSONObject verifyAccess(JSONObject originalResult, String id, String access){
        JSONObject jsonObject = accountDao.TverifyAccess(id,access);
        if(jsonObject.getIntValue("code")==0) {
            return originalResult;
        }
        else return jsonObject;
    }

    public JSONObject getEmailById(String Tid){
        try {
            Teacher t = tDao.TgetById(Tid);
            return ErrorService.getCode(0, t.getT_mail());
        }
        catch (Exception e){
            return ErrorService.getCode(1,"您的教工号可能输错了");
        }
    }





}
