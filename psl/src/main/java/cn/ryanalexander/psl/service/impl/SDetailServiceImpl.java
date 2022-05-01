package cn.ryanalexander.psl.service.impl;

import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.mapper.SDetailMapper;
import cn.ryanalexander.psl.service.SDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ryan
* @description 针对表【s_detail】的数据库操作Service实现
* @createDate 2022-04-17 20:46:59
*/
@Service
public class SDetailServiceImpl extends ServiceImpl<SDetailMapper, SDetailPO>
    implements SDetailService {
    @Resource
    private SDetailMapper sDetailMapper;

    public SDetailPO getSDetailByTeacherId(String teacherId, String year){
        return sDetailMapper.selectSDetailByTeacherId(teacherId, year);
    }
}




