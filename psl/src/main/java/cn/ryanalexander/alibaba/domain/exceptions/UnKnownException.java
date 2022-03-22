package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;

/**
 * @author ryan
 * @description TODO
 * @date 2022/3/22 18:42
 * @Version 1.0.0-Beta
 */
public class UnKnownException extends AppException{

    public UnKnownException(Class<?> clazz) {
        super(ErrorCodeEnum.FAIL, clazz.getName() +" Un-Found");
    }
}