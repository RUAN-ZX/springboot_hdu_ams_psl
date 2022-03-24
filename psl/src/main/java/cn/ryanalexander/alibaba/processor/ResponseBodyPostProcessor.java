package cn.ryanalexander.alibaba.processor;

import cn.ryanalexander.alibaba.domain.dto.Result;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.ServletResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ResponseBodyPostProcessor implements ResponseBodyAdvice<Object> {
	
	
	//定义不走控制器增强器的返回类型
    /**
     * <p><b>定义不走控制器增强器的返回类型</b></p>
     * 如果是Result对象 很可能是Exception跑出来的 我们不需要再包装
     *
     * 如果各种DTO 可以包装
     *
     * String 我想包装
     * <p>todo 2022-03-23 </p>
     * @since 1.0.0
    */
    private static final List<Class<?>> notSupports = Arrays.asList( void.class, Result.class, ServletResponse.class, ResponseEntity.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Class<?> parameterType = methodParameter.getParameterType();
        if (notSupports.contains(parameterType)) {
            return false;
        }
        return true;
    }
	
	//封装返回数据
    @Override
    public Result beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o != null) {
            return new Result(o);
        }
        return null;
    }

	
    // 全局异常捕捉处理-普通Exception
    @ResponseBody
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Throwable.class)
    public Result errorHandler(Throwable e) {
        log.error(e.toString(), e);
        return new Result(new ErrorCode(SubjectEnum.INTERNAL), e.getMessage());
    }


    // 全局异常捕捉处理-自定义Exception
    @ResponseBody
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = AppException.class)
    public Result errorHandler(AppException e) {
        log.error(e.toString(), e);
        return new Result(e.getErrorCode(), e.getExceptionInfoList(), e.getMessage());
    }


    // 全局异常捕捉处理-参数验证异常
    @ResponseBody
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result allExceptionHandler(MethodArgumentNotValidException e) {
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errMsg = new StringBuilder(bindingResult.getFieldErrors().size() * 16);
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            if (i > 0) {
                errMsg.append(",");
            }
            FieldError error = bindingResult.getFieldErrors().get(i);
            errMsg.append(error.getField() + ":" + error.getDefaultMessage());
        }
        return new Result(new ErrorCode(SubjectEnum.INTERNAL), errMsg.toString());
    }
}
