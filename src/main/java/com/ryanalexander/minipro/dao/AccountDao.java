package com.ryanalexander.minipro.dao;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.entries.E;
import com.ryanalexander.minipro.service.EmailService;
import com.ryanalexander.minipro.service.ErrorService;
import com.ryanalexander.minipro.service.TService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


// 底层Redis操作
@Repository
public class AccountDao {


    @Autowired
    private RedisTemplate<String, Object> ryanRedisTemplate;

    @Autowired TDao tDao;

    @Resource
    EmailService emailService;

    private void updateKey(String Tid, String eventName, String value,int nums, TimeUnit timeUnit){
        ryanRedisTemplate.opsForValue().set(Tid+":"+eventName,value, nums, timeUnit);
    }
    private JSONObject verifyKey(String Tid, String eventName, String value){

        String code = "";
        try{
            code = Objects.requireNonNull(ryanRedisTemplate.opsForValue().get(Tid + ":" + eventName)).toString();
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"找不到");
        }

        if(code.equals(value)) return ErrorService.getCode(0,"匹配成功");
        else return ErrorService.getCode(1,"有但是错了");

    }


    public void TupdateAccess(String Taccess, String Tid){
        updateKey(Tid,"Taccess",Taccess,1,TimeUnit.DAYS);

    }
    public void TupdateRefresh(String Trefresh, String Tid){
        updateKey(Tid,"Trefresh",Trefresh,7,TimeUnit.DAYS);

    }

    public void TupdateCaptcha(String Tcaptcha, String Tid) throws MessagingException {
        updateKey(Tid,"Tcaptcha",Tcaptcha,5,TimeUnit.MINUTES);
    }

    public JSONObject TverifyCaptcha(String Tid, String Tcaptcha){
        int code = verifyKey(Tid,"Tcaptcha",Tcaptcha).getIntValue("code");
        if(code==-1)
            return ErrorService.getCode(-1,"请再次尝试获取验证码");
        else if(code==0)
            return ErrorService.getCode(0,"验证码通过 验证成功");
        else if(code==1)
            return ErrorService.getCode(1,"验证失败");
        else return ErrorService.getCode(-1,"请再次尝试获取验证码");

    }

    public JSONObject TverifyAccess(String Tid, String Taccess){
        int code = verifyKey(Tid,"Taccess",Taccess).getIntValue("code");
        if(code==0) return ErrorService.getCode(0,"access token验证成功");
        else if(code==-1)
            // 这里设计 让客户端再次发起请求 用refresh token获取新的access token再说 而不是这次请求就连refresh token一起用了
            return ErrorService.getCode(-1,"access token过期 请尝试refresh");
        else return ErrorService.getCode(1,"access token 不可用");

    }

    public JSONObject Tverifyrefresh(String Tid, String Trefresh){
        int code = verifyKey(Tid,"Trefresh",Trefresh).getIntValue("code");
        if(code==0) return ErrorService.getCode(0,"Trefresh token验证成功");
        else if(code==-1)
            // 这里设计 让客户端再次发起请求 用refresh token获取新的access token再说 而不是这次请求就连refresh token一起用了
            return ErrorService.getCode(-1,"Trefresh token过期 ");
        else return ErrorService.getCode(1,"Trefresh token 不可用");

    }

    @Async
    public void getCaptcha_async(String Tid, String Tname) throws Exception{


        String Tcaptcha = TService.generateVerCode(Tid);
        emailService.sendMails(Tid,Tcaptcha, Tname);

        TupdateCaptcha(Tcaptcha,Tid);
    }
}
