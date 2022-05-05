package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.ClassPO;
import cn.ryanalexander.sst.service.ClassService;
import cn.ryanalexander.sst.mapper.ClassMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【class】的数据库操作Service实现
* @createDate 2022-05-04 15:31:36
*/
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, ClassPO>
    implements ClassService{

}




