package cn.ryanalexander.psl.mapper;

import cn.ryanalexander.psl.domain.po.S1DetailPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s1_detail】的数据库操作Mapper
* @createDate 2022-04-18 09:58:44
* @Entity cn.ryanalexander.alibaba.domain.po.S1DetailPO
*/
public interface S1DetailMapper extends BaseMapper<S1DetailPO> {
    ArrayList<Integer> selectAllYears();
}




