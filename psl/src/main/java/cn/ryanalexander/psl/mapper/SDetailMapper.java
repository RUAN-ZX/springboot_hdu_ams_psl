package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.SDetailPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ryan
 * @description 针对表【s_detail】的数据库操作Mapper
 * @createDate 2022-04-16 19:55:42
 * @Entity cn.ryanalexander.alibaba.domain.po.SDetailPO
 */
public interface SDetailMapper extends BaseMapper<SDetailPO> {
    SDetailPO selectSDetailByTeacherId(@Param("teacherId")String teacherId, @Param("year")String year);
}




