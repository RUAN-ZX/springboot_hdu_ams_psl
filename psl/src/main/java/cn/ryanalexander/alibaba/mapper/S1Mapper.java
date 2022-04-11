package cn.ryanalexander.alibaba.mapper;

import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s1】的数据库操作Mapper
* @createDate 2022-04-09 21:48:57
* @Entity cn.ryanalexander.alibaba.domain.po.S1
*/
public interface S1Mapper extends BaseMapper<SDetailPO> {
    ArrayList<Integer> selectAllYears();

}




