package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class AppException extends RuntimeException {

    private ErrorCodeEnum codeEnum;

    private ExceptionInfo exceptionInfo;

    public AppException(ErrorCodeEnum codeEnum) {
        super();
        this.codeEnum = codeEnum;
    }
    public AppException(ErrorCodeEnum codeEnum, String message) {
        super(message);
        this.codeEnum = codeEnum;
    }

    public AppException(ExceptionInfo exceptionInfo, ErrorCodeEnum codeEnum) {
        super();
        this.exceptionInfo = exceptionInfo;
        this.codeEnum = codeEnum;
    }

    public ErrorCodeEnum getCodeEnum() {
        return codeEnum;
    }

}
