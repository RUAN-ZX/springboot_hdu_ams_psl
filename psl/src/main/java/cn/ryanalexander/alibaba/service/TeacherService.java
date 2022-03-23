package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.domain.bo.excel.TidAndEmail;
import cn.ryanalexander.alibaba.domain.po.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service
* @createDate 2022-03-22 16:11:53
*/
public interface TeacherService extends IService<Teacher> {

    void updatePwdById(String t_id, String t_pwd);
}
