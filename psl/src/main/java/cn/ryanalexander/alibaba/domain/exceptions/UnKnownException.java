package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/23 </p>
 *
 * @author ryan 2022/3/23 21:58
 * @since 1.0.0
 **/
public class UnKnownException extends AppException{
    public UnKnownException(Class<?> clazz, String queryMethod) {
        super(ErrorCodeEnum.UNKNOWN, new ExceptionInfo(clazz.getName(), queryMethod, queryMethod));
    }
}
