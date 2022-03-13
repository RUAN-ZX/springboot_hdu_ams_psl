package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.dao.TeacherDao;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: TeacherService
 * @Description
 * @Author ryan
 * @Date 2022/3/13 19:54
 * @Version 1.0.0-Beta
 **/
public class TeacherService {
    @Resource
    private TeacherDao teacherDao;
    void TinsertByIdNameEmail(List list){
        teacherDao.TinsertByIdNameEmail(list);
    }

}
