package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.ThesisDesignPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【thesis_design】的数据库操作Mapper
* @createDate 2022-04-10 15:54:44
* @Entity cn.ryanalexander.alibaba.domain.po.ThesisDesignPO
*/
public interface ThesisDesignMapper extends BaseMapper<ThesisDesignPO> {
    ArrayList<String> selectYearRange(@Param("teacherId")String id);
}




