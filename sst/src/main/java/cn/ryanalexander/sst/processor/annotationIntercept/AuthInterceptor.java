package cn.ryanalexander.sst.processor.annotationIntercept;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

//    @Resource
//    private AccountService accountService;

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
        // 没必要设置为accountId 没必要
        if(require.value() != RoleEnum.EXPIRED){
            String access = httpServletRequest.getHeader("access");
            String accountId = httpServletRequest.getHeader("accountId");
//            accountService.verifyAccess(accountId, access);
            if(require.value() != RoleEnum.ROOT){

            }
        }
        else {
            String refresh = httpServletRequest.getHeader("refresh");
            String accountId = httpServletRequest.getParameterMap().get("accountId")[0];
//            accountService.verifyRefresh(accountId, refresh);
        }


        return true;
    }
}