package cn.ryanalexander.alibaba.domain;

/**
 * @ClassName: Order
 * @Description
 * @Author Ryan
 * @Date 2021/12/11 21:48
 * @Version 1.0.0-Beta
 **/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order
{
    private Long id;

    private Long userId;

    private Long productId;

    private Integer count;

    private BigDecimal money;

    private Integer status; //订单状态：0：创建中；1：已完结
}