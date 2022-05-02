package cn.ryanalexander.sst.processor.annotationIntercept;

import cn.ryanalexander.common.enums.AppKeyEnum;
import cn.ryanalexander.sst.service.AccountFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Lazy
    @Autowired
    private AccountFeignService accountFeignService;

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

        // todo 这里注意 其实使用userId 因为可以明确 请求的user和account是同一个人！
        RoleEnum role = require.value();
        int userId = Integer.parseInt(httpServletRequest.getParameterMap().get("accountId")[0]);

        if(role != RoleEnum.EXPIRED){
            String access = httpServletRequest.getHeader("access");
            accountFeignService.verifyAccess(userId, AppKeyEnum.SST.key, access);
        } else {
            String refresh = httpServletRequest.getHeader("refresh");
            accountFeignService.verifyRefresh(userId, AppKeyEnum.SST.key, refresh);
        }


        if(require.value() == RoleEnum.TEACHER){

        }
        else if(require.value() == RoleEnum.ROOT){

        }


        return true;
    }
}