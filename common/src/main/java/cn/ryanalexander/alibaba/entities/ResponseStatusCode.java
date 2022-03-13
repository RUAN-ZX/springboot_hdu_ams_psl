package cn.ryanalexander.alibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ResponseStatusCode
 * @Description
 * @Author Ryan
 * @Date 2021.11.7 21:28
 * @Version 1.0.0-Beta
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatusCode<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseStatusCode(Integer code, String message){
        this.code = code;
        this.message = message;
        this.data = (T) message;
    }
}
