package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Teacher;
import generator.service.TeacherService;
import generator.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2022-03-22 13:22:44
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

}




