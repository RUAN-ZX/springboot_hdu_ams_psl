package cn.ryanalexander.alibaba.service.impl;

import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.alibaba.domain.po.SFinalPO;
import cn.ryanalexander.alibaba.service.SFinalService;
import cn.ryanalexander.alibaba.mapper.SFinalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ryan
* @description 针对表【s_final】的数据库操作Service实现
* @createDate 2022-04-12 10:38:17
*/
@Service
public class SFinalServiceImpl extends ServiceImpl<SFinalMapper, SFinalPO>
    implements SFinalService{
    @Resource
    private SFinalMapper sFinalMapper;

    public SFinalPO getSFinalByTeacherId(String teacherId, String year){
        return sFinalMapper.selectSFinalByTeacherId(teacherId, year);
    }
}




