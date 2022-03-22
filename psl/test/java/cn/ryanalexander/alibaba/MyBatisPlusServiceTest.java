package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.po.Teacher;
import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import cn.ryanalexander.alibaba.service.TeacherService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date:2022/2/13
 * Author:ybc
 * Description:
 */
@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private TeacherService teacherService;

    private final Logger logger = LoggerFactory.getLogger(MyBatisPlusServiceTest.class);

    @Test
    public void testGetCount(){
        logger.warn("dfgdfg");
        teacherService.updatePwdById("40028", "sdf");
    }

    @Test
    public void testSelect(){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("teacher_name", "高");
        List<Teacher> list = teacherService.getBaseMapper().selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void getTime(){
        List<String> strList = Arrays.asList(SpringUtil.getApplicationContext().getBeanDefinitionNames());
        strList.forEach(System.out::println);

    }

}
