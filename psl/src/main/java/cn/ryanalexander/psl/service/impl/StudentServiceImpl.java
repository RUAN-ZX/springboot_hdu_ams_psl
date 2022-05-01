package cn.ryanalexander.psl.service.impl;

import cn.ryanalexander.psl.domain.po.StudentPO;
import cn.ryanalexander.psl.mapper.StudentMapper;
import cn.ryanalexander.psl.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【student】的数据库操作Service实现
* @createDate 2022-04-09 11:14:10
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, StudentPO>
    implements StudentService {

}




