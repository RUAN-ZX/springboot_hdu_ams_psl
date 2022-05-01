package cn.ryanalexander.common.domain.exceptions;


import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;

/**
 * @author ryan
 * @description TODO
 * @date 2022/3/22 18:35
 * @Version 1.0.0-Beta
 */
public class InjectionException extends AppException {
    // TODO: 2022/3/24
    public InjectionException(Class<?> clazz) {
        super(new ErrorCode(SubjectEnum.INTERNAL),
                clazz.getName() +" Injection Failed");
    }
}
