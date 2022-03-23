package cn.ryanalexander.alibaba.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/23 </p>
 *
 * @author ryan 2022/3/23 20:48
 * @since 1.0.0
 **/
@AllArgsConstructor
@Getter
public class ExceptionInfo {
    /**
     * <p><b>发生错误的可能主体</b></p>
     */
    private String item;
    /**
     * <p><b>发生错误的可能原因</b></p>
     */
    private String method;
    /**
     * <p><b>printStackTrace</b></p>
     */
    private String cause;
    /**
     * <p><b>什么方式导致错误</b></p>
     *  比如selectById 因为id不存在
     */
//    private String eStackTrace;
}
