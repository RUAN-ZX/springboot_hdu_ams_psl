package cn.ryanalexander.alibaba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.domain.po.TeacherPO;
import cn.ryanalexander.alibaba.service.TeacherService;
import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2022-04-11 14:56:26
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherPO>
    implements TeacherService{

}




