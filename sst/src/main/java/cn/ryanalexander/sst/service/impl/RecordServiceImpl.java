package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.RecordPO;
import cn.ryanalexander.sst.service.RecordService;
import cn.ryanalexander.sst.mapper.RecordMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【record】的数据库操作Service实现
* @createDate 2022-05-04 15:31:49
*/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordPO>
    implements RecordService{

}




