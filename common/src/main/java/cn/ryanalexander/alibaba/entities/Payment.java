package cn.ryanalexander.alibaba.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: Payment
 * @Description
 * @Author Ryan
 * @Date 2021.11.7 21:25
 * @Version 1.0.0-Beta
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long id;
    private String serial;
    private String name;
    public Payment(Long id, String serial){
        this.id = id;
        this.serial = serial;
        this.name = serial;
    }

}
