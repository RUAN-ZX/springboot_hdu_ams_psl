package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.AchievementPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【achievement】的数据库操作Mapper
* @createDate 2022-04-10 13:28:41
* @Entity cn.ryanalexander.alibaba.domain.po.Achievement
*/
public interface AchievementMapper extends BaseMapper<AchievementPO> {
    void saveOrUpdateBatch(@Param("items") ArrayList<AchievementPO> achievementPOS);
}




