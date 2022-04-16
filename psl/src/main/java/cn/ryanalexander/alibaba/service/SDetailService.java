package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.domain.po.SDetailPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ryan
* @description 针对表【s_detail】的数据库操作Service
* @createDate 2022-04-16 19:55:42
*/
public interface SDetailService extends IService<SDetailPO> {
    SDetailPO getSDetailByTeacherId(String teacherId, String year);
}
