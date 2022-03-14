package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.dao.TeacherDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TeacherService
 * @Description
 * @Author ryan
 * @Date 2022/3/13 19:54
 * @Version 1.0.0-Beta
 **/

// 还是由ExcelEntity调用Service的方法比较好 否则单独录入的时候 如果存在复用就很尴尬
@Service
public class TeacherService {
    @Resource
    private TeacherDao teacherDao;

    public void t_insertByIdNameEmail(ArrayList<?> list){
        teacherDao.t_insertByIdNameEmail(list);
    }

}
