package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.TeacherPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2022-04-11 14:56:26
* @Entity cn.ryanalexander.alibaba.domain.po.TeacherPO
*/
public interface TeacherMapper extends BaseMapper<TeacherPO> {
    void saveOrUpdateBatch(@Param("items") List<TeacherPO> list);
}




