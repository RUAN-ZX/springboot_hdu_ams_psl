package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;

public class AppException extends RuntimeException {

    private ErrorCodeEnum codeEnum;

    public AppException(ErrorCodeEnum codeEnum) {
        super();
        this.codeEnum = codeEnum;
    }

    public AppException(ErrorCodeEnum codeEnum, String message) {
        super(message);
        this.codeEnum = codeEnum;
    }

    public ErrorCodeEnum getCodeEnum() {
        return codeEnum;
    }

}
