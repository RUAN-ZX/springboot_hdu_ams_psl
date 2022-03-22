package cn.ryanalexander.alibaba.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: StorageDao
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:45
 * @Version 1.0.0-Beta
 **/
@Mapper
public interface StorageDao {
    //扣减库存
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);

}
