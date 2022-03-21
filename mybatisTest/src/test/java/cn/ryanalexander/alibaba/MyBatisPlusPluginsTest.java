package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.mapper.ProductMapper;
import cn.ryanalexander.alibaba.mapper.UserMapper;
import cn.ryanalexander.alibaba.pojo.Product;
import cn.ryanalexander.alibaba.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Date:2022/2/14
 * Author:ybc
 * Description:
 */
@SpringBootTest
public class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    Logger logger = LoggerFactory.getLogger(MyBatisPlusPluginsTest.class);
    @Test
    public void testPage(){
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPage(page, null);
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
        Page<User> page1 = null;
        logger.warn("fuck".equals(page1) == false?"fuck":"true" );
        logger.warn(!java.util.Objects.equals(page1, "ffa") ?"fuck":"true" );

    }

    @Test
    public void testPageVo(){
        // 使用slf4j 代码统一 到时候换实现也问题不大 对记录日志更是完全没有影响！

//        Logger logger = LogManager.getLogger(MyBatisPlusPluginsTest.class);
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPageVo(page, 20);
        logger.info("records: {}\n pages: {}\n total: {}\n",
                page.getRecords().toString(),
                page.getPages(),
                page.getTotal()
        );

        // 这里用于迭代的 只要hasNext就继续输出！ 前端也方便调用
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    @Test
    public void testProduct01(){
        //小李查询商品价格
        Product productLi = productMapper.selectById(1);
        System.out.println("小李查询的商品价格："+productLi.getPrice());
        //小王查询商品价格
        Product productWang = productMapper.selectById(1);
        System.out.println("小王查询的商品价格："+productWang.getPrice());
        //小李将商品价格+50
        productLi.setPrice(productLi.getPrice()+50);
        productMapper.updateById(productLi);
        //小王将商品价格-30
        productWang.setPrice(productWang.getPrice()-30);
        int result = productMapper.updateById(productWang);
        if(result == 0){
            //操作失败，重试
            Product productNew = productMapper.selectById(1);
            productNew.setPrice(productNew.getPrice()-30);
            productMapper.updateById(productNew);
        }
        //老板查询商品价格
        Product productLaoban = productMapper.selectById(1);
        System.out.println("老板查询的商品价格："+productLaoban.getPrice());
    }

}
