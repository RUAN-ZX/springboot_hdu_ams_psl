package cn.ryanalexander.alibaba.domain.dto;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.service.tool.TimeService;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class Result{

//    private final JSONObject jsonObject = new JSONObject();

    private Object data;

    private String msg;

    private int code;

//    private final long timestamp = new TimeService().getTimeStamp();
    private final String time = new TimeService().getTime();

    public Result(Object data) {
        this.data = data;
        this.code = ErrorCodeEnum.SUCCESS.getCode();
        this.msg = ErrorCodeEnum.SUCCESS.getCategory();
    }

    public Result(ErrorCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getCategory();
    }

    public Result(ErrorCodeEnum codeEnum, Object data) {
        this.data = data;
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getCategory();
    }

    public Result(ErrorCodeEnum codeEnum, String msg) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getCategory();
        if (!StringUtils.isEmpty(msg)) {
            this.msg = msg;
        }
    }
}
