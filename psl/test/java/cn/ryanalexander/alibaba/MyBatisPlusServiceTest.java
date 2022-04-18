package cn.ryanalexander.alibaba;

import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.service.AccountService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
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
        String a = "(2019-2020-1)-B0405450-42119-1合班";
        System.out.println(a.length());
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
}
