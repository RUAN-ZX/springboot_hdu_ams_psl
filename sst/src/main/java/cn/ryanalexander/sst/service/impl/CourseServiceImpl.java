package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.CoursePO;
import cn.ryanalexander.sst.service.CourseService;
import cn.ryanalexander.sst.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【course】的数据库操作Service实现
* @createDate 2022-04-27 21:34:07
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CoursePO>
    implements CourseService{

}




