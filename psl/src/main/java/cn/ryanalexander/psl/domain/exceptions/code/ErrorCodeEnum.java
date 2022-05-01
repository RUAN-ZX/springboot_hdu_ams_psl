package cn.ryanalexander.psl.domain.exceptions.code;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * <p><b>错误代码（前后端统一标准）
 * 用于分类错误 方便快速缩小自定义异常的范围</b></p>
 * Subject Behave Property Result
 * <p>2022-03-23 </p>

 * @since 1.0.0
 * @author RyanAlexander 2022-03-23 20:37
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    FAIL(-1, "General"),
    NOT_Found(-2, "Not Found"),
    INVALID(-3, "Code, Params Invalid or incompatible"),
    SUCCESS(0, "ok"),
    INJECTION_FAIL(1, "Injection failure"),
    SQL_EXEC_FAIL(2,"MySQL or mybatis execution failure"),
    UNKNOWN(3,"怎么可能有这种错误呢？"),
    RPC_FAIL(10, "RPC接口请求失败");

    private final int code;
    private final String category;
}
