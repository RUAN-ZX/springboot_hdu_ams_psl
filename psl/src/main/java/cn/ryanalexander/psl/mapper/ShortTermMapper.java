package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.ShortTermPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【short_term】的数据库操作Mapper
* @createDate 2022-04-08 18:39:02
* @Entity cn.ryanalexander.alibaba.domain.po.Evaluation.ShortTerm
*/
public interface ShortTermMapper extends BaseMapper<ShortTermPO> {
    ArrayList<String> selectTermRange(@Param("teacherId")String id);
}




