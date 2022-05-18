package cn.ryanalexander.psl;

import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Date:2022/2/13
 * Author:ybc
 * Description:
 */
@SpringBootTest
public class MyBatisPlusServiceTest {



    private final Logger logger = LoggerFactory.getLogger(MyBatisPlusServiceTest.class);



    @Test
    public void testGetCount(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("SSBsb3ZlIHRoZSBMWVEgaW4gaGVyID");
        System.out.println(pwd);
    }

    @Test
    public void getTime(){
        List<String> strList = Arrays.asList(SpringUtil.getApplicationContext().getBeanDefinitionNames());
        strList.forEach(System.out::println);

    }
    /**
     * 不创建对象的写
     */
    @Test
    public void noModelWrite() {
        // 写法1
        String fileName = "C:\\Users\\ryan\\Desktop\\fuck1.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板2").doWrite(dataList());
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<>(16);
        List<String> head0 = ListUtils.newArrayList();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("日期1" + System.currentTimeMillis());
        head2.add("fuck日期"); // 第二行

        List<String> head3 = ListUtils.newArrayList();
        head3.add("日期");
        List<String> head4 = ListUtils.newArrayList();
        head4.add("日期2");

        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
        list.add(head4);

        return list;
    }

    private List<Map<Integer, String>> dataList() {
        List<Map<Integer, String>> list = new ArrayList<>(16);
        for (int i = 0; i < 10; i++) {
            Map<Integer, String> data = new HashMap<>();
            data.put(0, new Date().toString());
            data.put(1, new Date().toString());
            data.put(2, new Date().toString());
            list.add(data);
        }
        return list;
    }
    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    @Test
    public void redis(){
        System.out.println("ufck");
//        for(String i : SpringUtil.getApplicationContext().getBeanDefinitionNames()){
//            System.out.println(i);
//        }
        redisTemplate.opsForValue().set("5053:captcha", "123456", 60*24*2, TimeUnit.MINUTES);
    }

    @Test
    public void fuck(){
        String a = "B040284G";
        System.out.println(a.charAt(7));
    }
}
