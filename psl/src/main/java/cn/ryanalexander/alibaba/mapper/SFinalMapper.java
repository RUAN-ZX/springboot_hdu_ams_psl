package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.domain.po.SFinalPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ryan
* @description 针对表【s_final】的数据库操作Mapper
* @createDate 2022-04-12 10:38:17
* @Entity cn.ryanalexander.alibaba.domain.po.SFinalPO
*/
public interface SFinalMapper extends BaseMapper<SFinalPO> {
    SFinalPO selectSFinalByTeacherId(@Param("teacherId")String teacherId, @Param("year")String year);
}




