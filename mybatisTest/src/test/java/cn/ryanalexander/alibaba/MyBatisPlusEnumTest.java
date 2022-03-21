package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.enums.SexEnum;
import cn.ryanalexander.alibaba.mapper.UserMapper;
import cn.ryanalexander.alibaba.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Date:2022/2/15
 * Author:ybc
 * Description:
 */
@SpringBootTest
public class MyBatisPlusEnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        User user = new User();
        user.setName("admin");
        user.setAge(33);
        user.setSex(SexEnum.MALE);
        int result = userMapper.insert(user);
        System.out.println("result:"+result);
    }

}
