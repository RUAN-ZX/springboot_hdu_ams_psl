package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.pojo.Product;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

/**
 * Date:2022/2/15
 * Author:ybc
 * Description:
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {
}
