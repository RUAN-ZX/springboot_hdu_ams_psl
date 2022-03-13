package cn.ryanalexander.alibaba.entity;

import cn.ryanalexander.alibaba.dao.TeacherDao;
import cn.ryanalexander.alibaba.entity.excel.ExcelEntity;
import cn.ryanalexander.alibaba.service.SpringUtil;
import cn.ryanalexander.alibaba.service.excel_ali.entity.TeacherSaveStrategy;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: Email
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:01
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("工号和邮箱")
public class TidAndEmail implements ExcelEntity {
    @ExcelProperty(value = "职工号")
    public String t_id;

    @ExcelProperty(value = "姓名")
    public String t_name;

    @ExcelProperty(value = "邮箱")
    public String t_mail;


    @Override
    public Object transformAndSave(List list, Object dao) {
        TeacherDao teacherDao = (TeacherDao) SpringUtil.getBean("teacherDao");
        try{
            teacherDao.TinsertByIdNameEmail(list);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return new Teacher();
    }
}
