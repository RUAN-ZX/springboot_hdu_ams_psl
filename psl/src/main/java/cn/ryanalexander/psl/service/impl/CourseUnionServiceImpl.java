package cn.ryanalexander.psl.service.impl;

import cn.ryanalexander.psl.mapper.CourseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import cn.ryanalexander.psl.service.CourseUnionService;
import cn.ryanalexander.psl.mapper.CourseUnionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author ryan
* @description 针对表【course_union】的数据库操作Service实现
* @createDate 2022-05-09 10:29:03
*/
@Service
public class CourseUnionServiceImpl extends ServiceImpl<CourseUnionMapper, CourseUnionPO>
    implements CourseUnionService{
    @Resource
    private CourseUnionMapper courseUnionMapper;
    @Override
    public List<CourseUnionPO> selectBatchByNums(ArrayList<String> courseNums) {
        return courseUnionMapper.selectBatchByNum(courseNums);
    }


}




