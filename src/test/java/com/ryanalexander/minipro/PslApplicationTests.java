package com.ryanalexander.minipro;


import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.EDao;
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
    private com.ryanalexander.minipro.service.StaticConfiguration StaticConfiguration;
    @Test
    void contextLoads() throws Exception {
        String t = "马保国老师（id:12312）";
        String captcha = "233333";
        String url = "D:\\Users\\Ryan\\IdeaProjects\\minipro_psl\\src\\main\\resources\\mail.html";
        String content = readFile(url);
        System.out.println(content);
        System.out.println(content.replace("老师",t));
        System.out.println(content.replace("123456",captcha));
    }

    @Autowired
    RedisTemplate ryanRedisTemplate;

    @Autowired
    EDao eDao;
    @Test
    void redisTest() throws InterruptedException {
//        Tredis tredis = new Tredis("123","123","123");


        System.out.println(ryanRedisTemplate.opsForValue().get("00001"));
        Thread.sleep(2000);
        System.out.println(ryanRedisTemplate.opsForValue().get("00001"));

    }


    @Test
    void CCidTrim(){
        String ccid = "(2019-2020-1)-B0405450-42119-1";
        Pattern r1 = Pattern.compile("\\((.*?)\\)");

        Matcher m = r1.matcher(ccid);
        String temp = "";
        if (m.find()) {
            temp = m.group(0);
        }
        temp = m.group(1);
        System.out.println(temp);
        System.out.println(m.group(1));

    }


}
