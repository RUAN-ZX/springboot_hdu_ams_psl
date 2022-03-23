package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;

/**
 * @author ryan
 * @description TODO
 * @date 2022/3/22 18:35
 * @Version 1.0.0-Beta
 */
public class InjectionException extends AppException{

    public InjectionException(Class<?> clazz) {
        super(ErrorCodeEnum.INJECTION_FAIL,
                clazz.getName() +" Injection Failed");
    }
}
