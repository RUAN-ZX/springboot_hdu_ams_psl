package cn.ryanalexander.alibaba.service.impl;

import cn.ryanalexander.alibaba.mapper.UserMapper;
import cn.ryanalexander.alibaba.pojo.User;
import cn.ryanalexander.alibaba.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Date:2022/2/13
 * Author:ybc
 * Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
