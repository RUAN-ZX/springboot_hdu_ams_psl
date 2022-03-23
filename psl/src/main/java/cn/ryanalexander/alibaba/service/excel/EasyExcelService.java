package cn.ryanalexander.alibaba.service.excel;


import cn.ryanalexander.alibaba.mapper.AccountMapper;
//import cn.ryanalexander.alibaba.service.excel_ali.entity.ExcelEntity;

import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EasyExcelService {


    @Resource
    private AccountMapper accountMapper;

//    @Resource

    public String CCidTrim(String ccid){
        Pattern r3 = Pattern.compile("^\\((.*?)\\)");

        Matcher m = r3.matcher(ccid);
        if(m.find())
            return m.group(1);
        else {
//            System.out.println("123123123");;'/
            return "";
        }
    }
    public List<String> strTrim (Object o){
        Pattern r1 = Pattern.compile("\\((.*?)\\)$");

        Pattern r2 = Pattern.compile("=(.*)$");

        Matcher m = r1.matcher(o.toString());
        List<String> result = new ArrayList<String>();
        if (m.find()) {
            String temp = m.group(1);
            String[] temp_ = temp.split(",");

            for (String s : temp_) {
                Matcher target = r2.matcher(s);
                if (target.find()) {
                    result.add(target.group(1));
                }
            }
            // 部门	教师职工号	姓名	参评人次	总得分	全校排名	学院排名
//            System.out.println(result);
        }
        return result;
    }

    public void save(ArrayList list, int size) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        System.out.println(list.toArray()[0]); // 每一批抽1个

    }
}