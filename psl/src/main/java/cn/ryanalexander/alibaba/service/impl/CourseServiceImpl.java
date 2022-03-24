package cn.ryanalexander.alibaba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.domain.po.Course;
import cn.ryanalexander.alibaba.service.CourseService;
import cn.ryanalexander.alibaba.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【course】的数据库操作Service实现
* @createDate 2022-03-24 20:01:11
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}




