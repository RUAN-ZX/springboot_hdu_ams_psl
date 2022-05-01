package cn.ryanalexander.psl.service.impl;

import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.mapper.S1DetailMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.psl.domain.po.S1DetailPO;
import cn.ryanalexander.psl.service.S1DetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s1_detail】的数据库操作Service实现
* @createDate 2022-04-18 09:58:44
*/
@Service
public class S1DetailServiceImpl extends ServiceImpl<S1DetailMapper, S1DetailPO>
    implements S1DetailService{
    @Resource
    private S1DetailMapper s1DetailMapper;

    public ArrayList<Integer> getAllYears(){
        return s1DetailMapper.selectAllYears();
    }

    public ArrayList<S1DetailPO> getS1DetailByYear(Integer year){
        return new ArrayList<S1DetailPO>(
                s1DetailMapper.selectList(new QueryWrapper<S1DetailPO>().like("s1_detail_year", year)));
    }
}




