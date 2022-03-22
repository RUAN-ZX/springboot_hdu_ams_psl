package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.domain.po.Teacher;
import cn.ryanalexander.alibaba.service.excel.entity.TeacherEntity_;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2022-03-22 12:19:45
* @Entity generator.domain.Teacher
*/
public interface TeacherMapper extends BaseMapper<Teacher> {
    /* insert */

    void insertByIdName(List<TeacherEntity_> list);
    void insertByIdNameEmail(ArrayList<?> list);

    /* update */
    void updatePwd(String Tid, String Tpwd);


    void updateEmail(String Tid, String Tmail);
    /* select */


    Teacher getById(String Tid);

}




