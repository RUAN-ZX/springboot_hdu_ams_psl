package cn.ryanalexander.alibaba.domain.dto;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.service.tool.TimeService;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.Date;

@Getter
public class Result<T> {

    private T data;

    private String msg;

    private final int code;

//    private final long timestamp = new TimeService().getTimeStamp();
    private final String time = new TimeService().getTime();

    public Result(T data) {
        this.data = data;
        this.code = ErrorCodeEnum.OK.getCode();
        this.msg = ErrorCodeEnum.OK.getMsg();
    }
    // 限制setter
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
    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }

}
