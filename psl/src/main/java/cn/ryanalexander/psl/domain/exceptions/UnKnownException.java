package cn.ryanalexander.psl.domain.exceptions;

import cn.ryanalexander.psl.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.psl.domain.exceptions.code.SubjectEnum;

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
        super(null, new ExceptionInfo(clazz.getName(), queryMethod, queryMethod), new ErrorCode(SubjectEnum.FAIL));
    }
}
