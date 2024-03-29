package cn.ryanalexander.psl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.psl.domain.po.CoursePO;
import cn.ryanalexander.psl.service.CourseService;
import cn.ryanalexander.psl.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【course】的数据库操作Service实现
* @createDate 2022-03-24 20:01:11
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CoursePO>
    implements CourseService{

}




