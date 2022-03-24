package cn.ryanalexander.alibaba.domain.exceptions;

import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.service.tool.EmailService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

/**
 * <p><b>自定义Exception</b></p>
 * 底层的Exception如果被上层捕获
 * 最终 只在Controller级别处理 输出最贴近用户那方面的报错
 * 系统传出去的时候 只是改写ErrorCode为系统问题 不是用户问题 其他的细节交给前端来做
 * 因为前端可以明确是哪个API调用返回的结果炸了 那个时候写 友好提示也方便
 *
 * 原来的思路是层层捕获 并且把最上层Controller的ExceptionInfo作为和前端对接的依据
 * 但是发现底层就可以独立输出最有价值的debug 以及判断是用户还是系统的问题 因此没必要各层决定ErrorCode
 * 最多 需要的话 叠加ExceptionInfo就好了
 *
 * 详细的报错信息会回传到前端来 方便debug！
 * 可能等到系统稳定 就不再需要传前端 因为费带宽 那个时候会查日志去
 *
 * 用户友好提示交给前端来做 记得变量化 到时候好改
 * 后端不干这事 不方便
 * <p>2022-03-24 TODO 普通exception的stackTrace不知道怎么传递 还是不用传递</p>

 * @since 1.0.0
 * @author RyanAlexander 2022-03-24 11:42
 */
@AllArgsConstructor
public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    @Getter // 默认大小足够了！
    private ArrayList<ExceptionInfo> exceptionInfoList = new ArrayList<>();

    public AppException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode codeEnum, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    // ExceptionInfo exceptionInfo 为当前层的info
    public AppException(AppException e, ExceptionInfo exceptionInfo, ErrorCode errorCode) {
        super();
        if(e != null)
            this.exceptionInfoList.addAll(e.getExceptionInfoList());
        this.exceptionInfoList.add(exceptionInfo);
        this.errorCode = errorCode;
    }

    // todo 目前打算 如果承接原始Exception 应当做这样的转换！
    //  这些一般发生在比较底层的地方
    public AppException(Exception e, String item, String method){
        ExceptionInfo exceptionInfo = new ExceptionInfo(item, method, e.getStackTrace()[0].toString());
        this.exceptionInfoList.add(exceptionInfo);
        this.errorCode = new ErrorCode(SubjectEnum.INTERNAL);
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
