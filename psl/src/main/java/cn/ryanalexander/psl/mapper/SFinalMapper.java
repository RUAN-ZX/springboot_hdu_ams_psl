package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.SFinalPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s_final】的数据库操作Mapper
* @createDate 2022-04-18 20:26:46
* @Entity cn.ryanalexander.alibaba.domain.po.SFinalPO
*/
public interface SFinalMapper extends BaseMapper<SFinalPO> {
    SFinalPO selectSFinalByTeacherId(@Param("teacherId")String teacherId, @Param("year")String year);

    void saveOrUpdateBatch(@Param("items")ArrayList<SFinalPO> sFinalPOS);
}




