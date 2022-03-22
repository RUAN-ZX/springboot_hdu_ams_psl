package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class Result<T> {

    private T data;

    private String msg;

    private int code;

    private long timestamp = System.currentTimeMillis();

    public Result(T data) {
        this.data = data;
        this.code = ErrorCodeEnum.OK.getCode();
        this.msg = ErrorCodeEnum.OK.getMsg();
    }

    public Result(ErrorCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public Result(T data, ErrorCodeEnum codeEnum) {
        this.data = data;
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public Result(ErrorCodeEnum codeEnum, String msg) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        if (!StringUtils.isEmpty(msg)) {
            this.msg = msg;
        }
    }

}
