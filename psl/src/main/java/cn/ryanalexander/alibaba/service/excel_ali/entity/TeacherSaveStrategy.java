package cn.ryanalexander.alibaba.service.excel_ali.entity;

import cn.ryanalexander.alibaba.dao.TeacherDao;
import cn.ryanalexander.alibaba.entity.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: TeacherSaveStrategy
 * @Description
 * @Author ryan
 * @Date 2022/3/13 21:51
 * @Version 1.0.0-Beta
 **/
@Service
public class TeacherSaveStrategy implements ExcelSaveStrategy{
    @Resource
    private TeacherDao teacherDao;
    @Override
    public Object transformAndSave(List list, Object dao) {
//        TeacherDao dao1 = (TeacherDao) dao;
//        TeacherDao teacherDao = (TeacherDao) SpringUtil.getBean("TeacherDao");
        try {
            System.out.println(teacherDao);
            teacherDao.TinsertByIdNameEmail(list);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return new Teacher();
    }
}
