package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.MissionPO;
import cn.ryanalexander.sst.service.MissionService;
import cn.ryanalexander.sst.mapper.MissionMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【mission】的数据库操作Service实现
* @createDate 2022-05-04 15:31:44
*/
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, MissionPO>
    implements MissionService{

}




