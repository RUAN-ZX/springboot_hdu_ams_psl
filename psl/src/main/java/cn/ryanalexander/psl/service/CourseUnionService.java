package cn.ryanalexander.psl.service;

import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
* @author ryan
* @description 针对表【course_union】的数据库操作Service
* @createDate 2022-05-09 10:29:03
*/
public interface CourseUnionService extends IService<CourseUnionPO> {
    List<CourseUnionPO> selectBatchByNums(ArrayList<String> courseNums);
}
