package cn.ryanalexander.psl.domain.exceptions;

import cn.ryanalexander.psl.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.psl.domain.exceptions.code.SubjectEnum;

/**
 * @author ryan
 * @description TODO
 * @date 2022/3/22 18:35
 * @Version 1.0.0-Beta
 */
public class InjectionException extends AppException{
    // TODO: 2022/3/24
    public InjectionException(Class<?> clazz) {
        super(new ErrorCode(SubjectEnum.INTERNAL),
                clazz.getName() +" Injection Failed");
    }
}
