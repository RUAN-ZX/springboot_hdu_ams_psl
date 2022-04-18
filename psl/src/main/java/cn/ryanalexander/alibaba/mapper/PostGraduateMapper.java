package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.domain.po.PostGraduatePO;
import cn.ryanalexander.alibaba.domain.po.ShoulderBothPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【post_graduate】的数据库操作Mapper
* @createDate 2022-04-10 13:58:13
* @Entity cn.ryanalexander.alibaba.domain.po.PostGraduate
*/
public interface PostGraduateMapper extends BaseMapper<PostGraduatePO> {
    void saveOrUpdateBatch(@Param("items") ArrayList<PostGraduatePO> postGraduatePOS);
}




