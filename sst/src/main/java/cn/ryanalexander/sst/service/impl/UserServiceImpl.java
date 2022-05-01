package cn.ryanalexander.sst.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ryanalexander.sst.domain.po.UserPO;
import cn.ryanalexander.sst.service.UserService;
import cn.ryanalexander.sst.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author ryan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-05-01 21:06:45
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO>
    implements UserService{

}




