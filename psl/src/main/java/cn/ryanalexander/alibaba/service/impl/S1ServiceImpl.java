package cn.ryanalexander.alibaba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import cn.ryanalexander.alibaba.service.S1Service;
import cn.ryanalexander.alibaba.mapper.S1Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
* @author ryan
* @description 针对表【s1】的数据库操作Service实现
* @createDate 2022-04-09 21:48:57
*/
// todo 还要做一个批量更新 如果源数据改变了 申请全局计算一次！ 然后调用计算程序 重算哪一年的数据
    // 一定让操作员仔细考虑再说 毕竟很耗费时间！ 如果搞错了时间 就要首先再覆盖搞错时间的数据 先恢复 然后再导入正确时间的数据！
    // 只要不做生成表 就不会计算 当然也有全局计算的API给调用！ 算哪一年的数据
@Service
public class S1ServiceImpl extends ServiceImpl<S1Mapper, SDetailPO>
    implements S1Service{
    @Resource
    private S1Mapper s1Mapper;

    public ArrayList<Integer> getAllYears(){
        return s1Mapper.selectAllYears();
    }

    public ArrayList<SDetailPO> getS1ByYear(Integer year){
        return new ArrayList<>(
                s1Mapper.selectList(new QueryWrapper<SDetailPO>().like("s1_year", year)));
    }
}




