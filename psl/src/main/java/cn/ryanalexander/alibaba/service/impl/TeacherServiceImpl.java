package cn.ryanalexander.alibaba.service.impl;

import cn.ryanalexander.alibaba.domain.exceptions.InjectionException;
import cn.ryanalexander.alibaba.domain.exceptions.UnKnownException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.domain.po.Teacher;
import cn.ryanalexander.alibaba.service.TeacherService;
import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Supplier;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2022-03-22 16:11:53
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public void updatePwdById(String t_id, String t_pwd){
        Optional<TeacherMapper> teacherMapper_ = Optional.ofNullable(teacherMapper);
        Teacher teacherNullable = teacherMapper_.orElseThrow(
            () -> new InjectionException(TeacherMapper.class)
        ).selectById(Integer.valueOf(t_id));
        Optional<Teacher> teacherOptional = Optional.ofNullable(teacherNullable);
        Teacher teacher = teacherOptional.orElseThrow(
            () -> new UnKnownException(Teacher.class)
        );
        teacher.setTeacherPwd(t_pwd);

        teacherMapper.updateById(teacher);
    }
}




