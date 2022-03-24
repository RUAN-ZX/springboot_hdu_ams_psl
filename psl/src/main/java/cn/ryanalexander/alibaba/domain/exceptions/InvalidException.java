package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;

/**
 * <p><b></b></p>
 *  // TODO: 2022/3/24
 * <p> </p>
 *
 * @author
 * @since 1.0.0
 **/
public class InvalidException extends AppException{
    public InvalidException(Class<?> clazz, String verifyMethod, String cause) {
        super(null, new ExceptionInfo(clazz.getName(), verifyMethod, cause), new ErrorCode(SubjectEnum.USER));
    }
}
