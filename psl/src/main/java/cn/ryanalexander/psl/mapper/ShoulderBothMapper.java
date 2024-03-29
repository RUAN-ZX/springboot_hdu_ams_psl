package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.ShoulderBothPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【shoulder_both】的数据库操作Mapper
* @createDate 2022-04-10 13:29:33
* @Entity cn.ryanalexander.alibaba.domain.po.ShoulderBoth
*/
public interface ShoulderBothMapper extends BaseMapper<ShoulderBothPO> {
    void saveOrUpdateBatch(@Param("items") ArrayList<ShoulderBothPO> shoulderBothPOS);
}




