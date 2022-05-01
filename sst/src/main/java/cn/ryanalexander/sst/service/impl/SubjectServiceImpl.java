package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.SubjectPO;
import cn.ryanalexander.sst.service.SubjectService;
import cn.ryanalexander.sst.mapper.SubjectMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【subject】的数据库操作Service实现
* @createDate 2022-04-27 21:34:49
*/
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectPO>
    implements SubjectService{

}




