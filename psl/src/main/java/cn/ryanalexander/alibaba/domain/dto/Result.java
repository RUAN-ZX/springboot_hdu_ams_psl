package cn.ryanalexander.alibaba.domain.dto;

import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.service.tool.TimeService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
public class Result{

//    private final JSONObject jsonObject = new JSONObject();

    private Object data;

    private String msg; // 有错误的时候才用得到 对的时候 如果有数据 data传 附带信息msg 没数据 data传附带信息

    private String code;

//    private final long timestamp = new TimeService().getTimeStamp();
    private final String time = new TimeService().getTime();

    // 用于成功返回的情况
    public Result(Object data) {
        this.data = data;
        this.code = ErrorCode.getOK();
        this.msg = ErrorCode.getOK();
    }
    public Result() {
        this.code = ErrorCode.getOK();
        this.msg = ErrorCode.getOK();
    }


    public Result(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

//    public Result(ErrorCode errorCode, Object data) {
//        this.data = data;
//        this.code = errorCode.getCode();
//        this.msg = errorCode.getMsg();
//    }

    public Result(ErrorCode errorCode, String msg) {
        this.code = errorCode.getCode();
        this.msg = msg;
    }

    public Result(ErrorCode errorCode, Object data, String msg){
        this.code = errorCode.getCode();
        this.msg = msg;
        this.data = data;
    }
}
