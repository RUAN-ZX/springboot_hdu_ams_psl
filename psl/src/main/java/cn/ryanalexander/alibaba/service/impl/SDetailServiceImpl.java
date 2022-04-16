package cn.ryanalexander.alibaba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import cn.ryanalexander.alibaba.service.SDetailService;
import cn.ryanalexander.alibaba.mapper.SDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ryan
* @description 针对表【s_detail】的数据库操作Service实现
* @createDate 2022-04-16 19:55:42
*/
@Service
public class SDetailServiceImpl extends ServiceImpl<SDetailMapper, SDetailPO>
    implements SDetailService{
    @Resource
    private SDetailMapper sDetailMapper;

    public SDetailPO getSDetailByTeacherId(String teacherId, String year){
        return sDetailMapper.selectSDetailByTeacherId(teacherId, year);
    }
}




