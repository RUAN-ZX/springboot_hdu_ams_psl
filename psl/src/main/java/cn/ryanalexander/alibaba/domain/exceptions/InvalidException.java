package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;

/**
 * <p><b></b></p>
 *
 * <p> </p>
 *
 * @author
 * @since 1.0.0
 **/
public class InvalidException extends AppException{
    public InvalidException(Class<?> clazz, String verifyMethod, String cause) {
        super(ErrorCodeEnum.INVALID, new ExceptionInfo(clazz.getName(), verifyMethod, cause));
    }
}
