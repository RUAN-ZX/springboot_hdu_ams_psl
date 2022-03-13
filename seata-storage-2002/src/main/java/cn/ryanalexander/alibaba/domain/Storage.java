package cn.ryanalexander.alibaba.domain;

/**
 * @ClassName: Storage
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:44
 * @Version 1.0.0-Beta
**/
import lombok.Data;

@Data
public class Storage {

    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 已用库存
     */
    private Integer used;

    /**
     * 剩余库存
     */
    private Integer residue;
}
