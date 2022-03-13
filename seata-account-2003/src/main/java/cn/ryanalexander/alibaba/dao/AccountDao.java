package cn.ryanalexander.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @ClassName: AccountDao
 * @Description
 * @Author Ryan
 * @Date 2021/12/12 10:54
 * @Version 1.0.0-Beta
 **/
@Mapper
public interface AccountDao {
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
