package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.domain.po.SFinalPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ryan
* @description 针对表【s_final】的数据库操作Service
* @createDate 2022-04-18 20:26:46
*/
public interface SFinalService extends IService<SFinalPO> {
    SFinalPO getSFinalByTeacherId(String teacherId, String year);
}
