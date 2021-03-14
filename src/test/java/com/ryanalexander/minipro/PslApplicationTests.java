package com.ryanalexander.minipro;


import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.EDao;
import com.ryanalexander.minipro.service.StaticConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestExecutionListeners;

import javax.annotation.Resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ryanalexander.minipro.service.EmailService.readFile;


@SpringBootTest
class PslApplicationTests {
    @Resource
    StaticConfiguration staticConfiguration;

    @Test
    void test(){
        System.out.println(
                staticConfiguration.getCaptchaExpire());
    }
}
