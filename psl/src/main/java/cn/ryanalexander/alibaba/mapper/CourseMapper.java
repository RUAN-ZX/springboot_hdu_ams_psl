package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.domain.po.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【course】的数据库操作Mapper
* @createDate 2022-03-24 20:01:11
* @Entity cn.ryanalexander.alibaba.domain.po.Course
*/
public interface CourseMapper extends BaseMapper<Course> {
    void saveBatch(@Param("items") ArrayList<Course> list);
}




