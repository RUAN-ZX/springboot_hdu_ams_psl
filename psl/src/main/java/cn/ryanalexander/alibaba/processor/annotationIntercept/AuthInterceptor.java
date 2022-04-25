package cn.ryanalexander.alibaba.processor.annotationIntercept;

import cn.ryanalexander.alibaba.config.redis.RedisKeyEnum;
import cn.ryanalexander.alibaba.service.AccountService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse response, Object handler){
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Require require = method.getAnnotation(Require.class);
        // 判断接口是否有Require注解
        if (null == require) return true;
        // todo 区分简单的几个身份！
        if(require.value() != RoleEnum.EXPIRED){
            String access = httpServletRequest.getHeader("access");
            String accountId = httpServletRequest.getParameterMap().get("accountId")[0];
            accountService.verifyAccess(accountId, access);
            if(require.value() != RoleEnum.ROOT){

            }
            if(require.value() != RoleEnum.MANAGER){

            }
        }
        else {
            String refresh = httpServletRequest.getHeader("refresh");
            String accountId = httpServletRequest.getParameterMap().get("accountId")[0];
            accountService.verifyRefresh(accountId, refresh);
        }


        return true;
    }
}