package cn.ryanalexander.alibaba.domain.enumable;

import lombok.Getter;



@Getter
public enum ErrorCodeEnum {
    FAIL(-1, "未处理异常"),
    OK(1, "ok"),
    RPC_FAIL(2, "rpc接口请求失败");


    private int code;
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

}
