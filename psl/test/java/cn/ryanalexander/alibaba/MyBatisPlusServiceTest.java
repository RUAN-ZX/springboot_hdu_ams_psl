package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.service.AccountService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * Date:2022/2/13
 * Author:ybc
 * Description:
 */
@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private AccountService accountService;

    private final Logger logger = LoggerFactory.getLogger(MyBatisPlusServiceTest.class);

    @Test
    public void testGetCount(){
        logger.warn("dfgdfg");
        accountService.updatePwdById("40028", "sdf");
    }

    @Test
    public void testSelect(){
        QueryWrapper<AccountPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("teacher_name", "高");
        List<AccountPO> list = accountService.getBaseMapper().selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void getTime(){
        List<String> strList = Arrays.asList(SpringUtil.getApplicationContext().getBeanDefinitionNames());
        strList.forEach(System.out::println);

    }

}
