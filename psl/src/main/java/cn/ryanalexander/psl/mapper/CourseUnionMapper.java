package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
* @author ryan
* @description 针对表【course_union】的数据库操作Mapper
* @createDate 2022-05-09 10:29:03
* @Entity cn.ryanalexander.psl.domain.po.CourseUnionPO
*/
public interface CourseUnionMapper extends BaseMapper<CourseUnionPO> {
    ArrayList<CourseUnionPO> selectBatchByNum(@Param("items") List<String> list);
}




