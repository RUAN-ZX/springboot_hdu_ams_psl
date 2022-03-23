package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;

/**
 * @author ryan
 * @description TODO
 * @date 2022/3/22 18:42
 * @Version 1.0.0-Beta
 */
public class NotFoundException extends AppException{

    public NotFoundException(Class<?> clazz, String queryMethod, String cause) {
        super(ErrorCodeEnum.NOT_Found, new ExceptionInfo(clazz.getName(), queryMethod, cause));
    }
    /**
     * <p><b>简化版本</b></p>
     * 有时候从queryMethod就能看出来大致可能的错误 自然没必要写什么了
     * <p>todo 2022-03-23 </p>
     * @param clazz 找不到的 但是expected的类
        * @param queryMethod 找这个类的原定方法（当然现在失败了）
     * @since 1.0.0
    */
    public NotFoundException(Class<?> clazz, String queryMethod) {
        super(ErrorCodeEnum.NOT_Found, new ExceptionInfo(clazz.getName(), queryMethod, queryMethod));
    }

}