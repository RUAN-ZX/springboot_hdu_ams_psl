package cn.ryanalexander.alibaba.service.impl;

import cn.ryanalexander.alibaba.domain.po.Teacher;
import cn.ryanalexander.alibaba.mapper.TeacherDao;
import cn.ryanalexander.alibaba.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2022-03-22 12:19:45
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    public void insertByIdNameEmail(ArrayList<?> list){

//        teacherMapper.insert(new Teacher());
        teacherMapper.insertByIdNameEmail(list);
    }
}




